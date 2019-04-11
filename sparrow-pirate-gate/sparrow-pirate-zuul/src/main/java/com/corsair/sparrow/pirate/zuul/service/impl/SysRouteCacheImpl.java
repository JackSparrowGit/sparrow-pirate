package com.corsair.sparrow.pirate.zuul.service.impl;

import com.corsair.sparrow.pirate.zuul.domain.bean.SysZuulRoute;
import com.corsair.sparrow.pirate.zuul.service.SysRouteCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jack
 */
@Component
public class SysRouteCacheImpl implements SysRouteCache {

    private static final String SYS_ROUTE_CACHE_NAME = "SYS_ROUTE_CACHE_NAME";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Cacheable(cacheNames = SYS_ROUTE_CACHE_NAME)
    @Override
    public List<SysZuulRoute> getSysZuulRouteList() {
        List<SysZuulRoute> sysRouteCacheList = jdbcTemplate.query(
          "select * from t_sys_zuul_route where enabled = true",
          new BeanPropertyRowMapper<>(SysZuulRoute.class)
        );
        return sysRouteCacheList;
    }
}
