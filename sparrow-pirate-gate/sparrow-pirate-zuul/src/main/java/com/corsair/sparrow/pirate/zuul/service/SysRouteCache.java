package com.corsair.sparrow.pirate.zuul.service;

import com.corsair.sparrow.pirate.zuul.domain.bean.SysZuulRoute;

import java.util.List;

public interface SysRouteCache {

    /**
     * 获取路由缓存列表
     * @return
     */
    List<SysZuulRoute> getSysZuulRouteList();
}
