package com.corsair.sparrow.pirate.zuul.config;

import com.corsair.sparrow.pirate.zuul.domain.bean.SysZuulRoute;
import com.corsair.sparrow.pirate.zuul.service.SysRouteCache;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.util.StringUtils;

import java.util.*;

@Slf4j
public class CustomerRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {

    private ZuulProperties zuulProperties;

    @Autowired
    private SysRouteCache sysRouteCache;

    public CustomerRouteLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
        this.zuulProperties = properties;
    }

    @Override
    public void refresh() {
        doRefresh();
    }

    @Override
    protected Map<String, ZuulProperties.ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulProperties.ZuulRoute> zuulRouteLinkedHashMap = Maps.newLinkedHashMap();
        log.info("路由refresh时间:{}",new Date().toLocaleString());
        zuulRouteLinkedHashMap.putAll(super.locateRoutes());
        zuulRouteLinkedHashMap.putAll(locateRoutesFromCache());

        LinkedHashMap<String,ZuulProperties.ZuulRoute> routeLinkedHashMap = Maps.newLinkedHashMap();

        zuulRouteLinkedHashMap.entrySet().stream().forEach(stringZuulRouteEntry -> {
            String path = stringZuulRouteEntry.getKey();
            log.info("路由匹配: {}",path);
            if(!path.startsWith("/")){
                path = "/"+path;
            }
            if(StringUtils.hasText(this.zuulProperties.getPrefix())){
                path = this.zuulProperties + path;
                if(!path.startsWith("/")){
                    path = "/"+path;
                }
            }
            routeLinkedHashMap.put(path,stringZuulRouteEntry.getValue());
        });

        return routeLinkedHashMap;
    }

    private Map<String,ZuulProperties.ZuulRoute>  locateRoutesFromCache() {
        Map<String,ZuulProperties.ZuulRoute> zuulRouteMap = Maps.newLinkedHashMap();
        // 从缓存中获取zuul routes
        List<SysZuulRoute> sysZuulRoutes = sysRouteCache.getSysZuulRouteList();
        for (SysZuulRoute sysZuulRoute : sysZuulRoutes){
            if(StringUtils.isEmpty(sysZuulRoute.getPath())){
                continue;
            }
            if (StringUtils.isEmpty(sysZuulRoute.getServiceId()) && StringUtils.isEmpty(sysZuulRoute.getUrl())){
                continue;
            }
            ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
            zuulRoute.setId(sysZuulRoute.getId());
            zuulRoute.setPath(sysZuulRoute.getPath());
            zuulRoute.setServiceId(sysZuulRoute.getServiceId());
            zuulRoute.setUrl(sysZuulRoute.getUrl());
            zuulRoute.setStripPrefix(sysZuulRoute.getStripPrefix());
            zuulRoute.setRetryable(sysZuulRoute.getRetryable());
            if(Objects.nonNull(sysZuulRoute.getSensitiveHeaders()) && !StringUtils.isEmpty(sysZuulRoute.getSensitiveHeaders())){
                Set<String> sensitiveSets = Sets.newHashSet(sysZuulRoute.getSensitiveHeaders().split(","));
                zuulRoute.setSensitiveHeaders(sensitiveSets);
            }
            zuulRouteMap.put(zuulRoute.getPath(),zuulRoute);
        }
        return zuulRouteMap;
    }
}
