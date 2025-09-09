package com.universitymanagement.multitenancy;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
public class CustomMultiTenantConnectionProvider
        extends org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    private final Map<String, DataSource> dataSources = new HashMap<>();

    public CustomMultiTenantConnectionProvider() {
        // Preconfigure default tenant
        dataSources.put("tenant1_db", createAndCacheDataSource("tenant1_db"));
    }

    private DataSource createAndCacheDataSource(String dbName) {
        if (dataSources.containsKey(dbName)) {
            return dataSources.get(dbName); // reuse
        }

        try {
            HikariDataSource ds = buildDataSource(dbName);

            // Test connection
            try (Connection con = ds.getConnection()) {
                System.out.println("Connected to database: " + dbName);
            }

            dataSources.put(dbName, ds);
            return ds;

        } catch (Exception e) {
            // If DB doesn’t exist → create it
            System.out.println(e.getMessage());
            createDatabase(dbName);

            // Create datasource again
            HikariDataSource ds = buildDataSource(dbName);

            // 🔥 Run schema generation so tables exist
            generateSchema(ds);

            dataSources.put(dbName, ds);
            return ds;
        }
    }

    private HikariDataSource buildDataSource(String dbName) {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/" + dbName);
        ds.setUsername("root");
        ds.setPassword("root123");
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return ds;
    }

    private void createDatabase(String dbName) {
        String url = "jdbc:mysql://localhost:3306/";
        String username = "root";
        String password = "root123";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
            System.out.println("Database created: " + dbName);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to create database " + dbName, ex);
        }
    }


    private void generateSchema(DataSource dataSource) {
        try {
            LocalContainerEntityManagerFactoryBean emfBean = new LocalContainerEntityManagerFactoryBean();
            emfBean.setDataSource(dataSource);
            emfBean.setPackagesToScan("com.universitymanagement.entities");
            emfBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
            emfBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

            Properties jpaProps = new Properties();
            jpaProps.put("hibernate.hbm2ddl.auto", "update"); // create or update tables
            jpaProps.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
            emfBean.setJpaProperties(jpaProps);

            emfBean.afterPropertiesSet();
            emfBean.getObject().close(); // close after schema created
            System.out.println(" Schema generated for tenant DB.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate schema for tenant", e);
        }
    }

    @Override
    public DataSource selectAnyDataSource() {
        return dataSources.get("tenant1_db"); // default
    }

    @Override
    protected DataSource selectDataSource(Object tenantIdentifier) {
        String tenantId = String.valueOf(tenantIdentifier);
        return createAndCacheDataSource(tenantId);
    }
}
