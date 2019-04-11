package com.corsair.sparrow.pirate.oauth.service;

import com.corsair.sparrow.pirate.oauth.domain.bean.SysPermission;
import com.baomidou.mybatisplus.extension.service.IService;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.corsair.sparrow.pirate.core.base.PagingRequest;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author jack
 * @since 2019-03-22
 */
public interface ISysPermissionService extends IService<SysPermission> {

    /**
    * 默认带条件参数分页
    * @param pagingRequest
    * @param queryWrapper
    * @return
    */
    PageInfo<SysPermission> getPageInfo(PagingRequest pagingRequest, Wrapper<SysPermission> queryWrapper);

}
