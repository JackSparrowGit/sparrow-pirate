package com.corsair.sparrow.pirate.zuul.config;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.List;
import java.util.Objects;

/**
 * @author jack
 */
@Slf4j
@Component
@Primary
@ConditionalOnMissingBean(name = "swagger2ResourcesProvider")
public class Swagger2ResourcesProvider implements SwaggerResourcesProvider {
    /**
     * 注入动态路由
     */
    private CustomerRouteLocator customerRouteLocator;

    public Swagger2ResourcesProvider(CustomerRouteLocator customerRouteLocator) {
        this.customerRouteLocator = customerRouteLocator;
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> swaggerResourceList = Lists.newArrayList();
        List<Route> routeList = customerRouteLocator.getRoutes();
        if(Objects.nonNull(routeList)){
            routeList.stream().forEach(route -> {
                log.info("路由path: {}",route);
                swaggerResourceList.add(swaggerResource(route.getId(),route.getFullPath().replace("**","/v2/api-docs")));
            });
        }
        return swaggerResourceList;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
