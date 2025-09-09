package com.universitymanagement.multitenancy;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {



        @Override
        public String resolveCurrentTenantIdentifier() {
            String tenant = TenantContext.getCurrentTenant();
            return (tenant != null) ? tenant : "tenant1_db"; // fallback
        }

        @Override
        public boolean validateExistingCurrentSessions() {
            return true;
        }
    }


