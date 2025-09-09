package com.universitymanagement.configuration;

import com.universitymanagement.multitenancy.CustomMultiTenantConnectionProvider;
import com.universitymanagement.multitenancy.TenantIdentifierResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
public class ConfigurationHibernate {

    @Autowired
    private CustomMultiTenantConnectionProvider multiTenantConnectionProvider;

    @Autowired
    private TenantIdentifierResolver tenantIdentifierResolver;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        // Default DataSource (for Hibernate bootstrap)
        DataSource defaultDataSource = multiTenantConnectionProvider.selectAnyDataSource();

        // Hibernate properties
        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.multiTenancy", "DATABASE"); // use string
        jpaProperties.put("hibernate.multi_tenant_connection_provider", multiTenantConnectionProvider);
        jpaProperties.put("hibernate.tenant_identifier_resolver", tenantIdentifierResolver);
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        jpaProperties.put("hibernate.show_sql", true);
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");

        // EntityManagerFactory
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(defaultDataSource);
        emf.setPackagesToScan("com.universitymanagement.entities"); // your entity package
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.setJpaPropertyMap(jpaProperties);

        return emf;
    }
}
