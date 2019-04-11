package com.corsair.sparrow.pirate.oauth.service;

import com.corsair.sparrow.pirate.oauth.domain.bean.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.corsair.sparrow.pirate.core.base.PagingRequest;
import com.github.pagehelper.PageInfo;

import java.util.Set;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author jack
 * @since 2019-03-22
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
    * 默认带条件参数分页
    * @param pagingRequest
    * @param queryWrapper
    * @return
    */
    PageInfo<SysRole> getPageInfo(PagingRequest pagingRequest, Wrapper<SysRole> queryWrapper);

    /**
     * 根据userId查找SysRole Set集合
     * @param userId
     * @return
     */
    Set<SysRole> getRoleSetByUserId(Long userId);
}
