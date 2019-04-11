package com.corsair.sparrow.pirate.oauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.corsair.sparrow.pirate.oauth.domain.bean.SysRole;
import com.corsair.sparrow.pirate.oauth.domain.bean.SysUserRole;
import com.corsair.sparrow.pirate.oauth.mapper.SysRoleMapper;
import com.corsair.sparrow.pirate.oauth.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.corsair.sparrow.pirate.oauth.service.ISysUserRoleService;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.corsair.sparrow.pirate.core.base.PagingRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author jack
 * @since 2019-03-22
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @Override
    public PageInfo<SysRole> getPageInfo(PagingRequest pagingRequest, Wrapper<SysRole> queryWrapper) {
      PageHelper.startPage(pagingRequest.getPageNum(),pagingRequest.getPageSize());
      return new PageInfo<>(super.list(queryWrapper));
    }

    @Override
    public Set<SysRole> getRoleSetByUserId(Long userId) {
        QueryWrapper<SysUserRole> sysUserRoleQueryWrapper = new QueryWrapper<>();
        sysUserRoleQueryWrapper.eq("user_id",userId);
        List<SysUserRole> sysUserRoleList = sysUserRoleService.list(sysUserRoleQueryWrapper);
        // 获取sysUserRoleList中的roleSet集合
        Set<Long> roleIdsSet = sysUserRoleList.stream().map(SysUserRole::getRoleId).collect(Collectors.toSet());
        // 将SysRoleList转化为SysRoleSet并返回
        return Sets.newHashSet(super.listByIds(roleIdsSet));
    }
}
