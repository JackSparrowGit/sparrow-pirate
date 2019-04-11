package com.corsair.sparrow.pirate.oauth.service.impl;

import com.corsair.sparrow.pirate.oauth.domain.bean.SysRolePermission;
import com.corsair.sparrow.pirate.oauth.mapper.SysRolePermissionMapper;
import com.corsair.sparrow.pirate.oauth.service.ISysRolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.corsair.sparrow.pirate.core.base.PagingRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 角色权限关联表 服务实现类
 * </p>
 *
 * @author jack
 * @since 2019-03-22
 */
@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements ISysRolePermissionService {

    @Override
    public PageInfo<SysRolePermission> getPageInfo(PagingRequest pagingRequest, Wrapper<SysRolePermission> queryWrapper) {
      PageHelper.startPage(pagingRequest.getPageNum(),pagingRequest.getPageSize());
      return new PageInfo<>(super.list(queryWrapper));
    }
}
