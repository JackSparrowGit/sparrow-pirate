package com.corsair.sparrow.pirate.oauth.service.impl;

import com.corsair.sparrow.pirate.oauth.domain.bean.SysUserRole;
import com.corsair.sparrow.pirate.oauth.mapper.SysUserRoleMapper;
import com.corsair.sparrow.pirate.oauth.service.ISysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.corsair.sparrow.pirate.core.base.PagingRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 用户角色关联表 服务实现类
 * </p>
 *
 * @author jack
 * @since 2019-03-22
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Override
    public PageInfo<SysUserRole> getPageInfo(PagingRequest pagingRequest, Wrapper<SysUserRole> queryWrapper) {
      PageHelper.startPage(pagingRequest.getPageNum(),pagingRequest.getPageSize());
      return new PageInfo<>(super.list(queryWrapper));
    }
}
