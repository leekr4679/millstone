package com.base.app.security.service;

import com.base.app.domain.system.resource.Resources;
import com.base.app.domain.system.resource.ResourcesRepository;
import com.base.app.domain.system.resource.ResourcesService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class SecurityResourceService {

    private ResourcesService resourcesService;

    public SecurityResourceService(ResourcesService resourcesService) {
        this.resourcesService = resourcesService;
    }

    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceList() {
        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> result = new LinkedHashMap<>();
        List<Resources> resourcesList = resourcesService.list("url");

        resourcesList.forEach(resource -> {
            List<ConfigAttribute> configAttributeList = new ArrayList<>();
            resource.getRoleSet().forEach(role -> {
                configAttributeList.add(new SecurityConfig(role.getRoleCd()));
                result.put(new AntPathRequestMatcher(resource.getResourceNm()), configAttributeList);
            });
        });

        return result;
    }
}
