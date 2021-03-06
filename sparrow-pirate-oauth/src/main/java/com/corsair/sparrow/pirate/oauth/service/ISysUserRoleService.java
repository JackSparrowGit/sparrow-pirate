package com.corsair.sparrow.pirate.oauth.service;

import com.corsair.sparrow.pirate.oauth.domain.bean.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.corsair.sparrow.pirate.core.base.PagingRequest;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 用户角色关联表 服务类
 * </p>
 *
 * @author jack
 * @since 2019-03-22
 */
public interface ISysUserRoleService extends IService<SysUserRole> {

    /**
    * 默认带条件参数分页
    * @param pagingRequest
    * @param queryWrapper
    * @return
    */
    PageInfo<SysUserRole> getPageInfo(PagingRequest pagingRequest, Wrapper<SysUserRole> queryWrapper);

}
