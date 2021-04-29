package com.base.app.security;

import com.base.app.domain.system.resource.ResourcesRepository;
import com.base.app.domain.system.resource.ResourcesService;
import com.base.app.security.service.SecurityResourceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AppConfig {

    @Bean
    public SecurityResourceService securityResourceService(ResourcesService resourcesService) {
        SecurityResourceService securityResourceService = new SecurityResourceService(resourcesService);
        return securityResourceService;
    }
}
