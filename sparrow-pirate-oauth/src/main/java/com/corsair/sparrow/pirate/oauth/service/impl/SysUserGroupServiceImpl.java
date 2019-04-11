package com.corsair.sparrow.pirate.oauth.service.impl;

import com.corsair.sparrow.pirate.oauth.domain.bean.SysUserGroup;
import com.corsair.sparrow.pirate.oauth.mapper.SysUserGroupMapper;
import com.corsair.sparrow.pirate.oauth.service.ISysUserGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.corsair.sparrow.pirate.core.base.PagingRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 用户-用户组关联表 服务实现类
 * </p>
 *
 * @author jack
 * @since 2019-03-22
 */
@Service
public class SysUserGroupServiceImpl extends ServiceImpl<SysUserGroupMapper, SysUserGroup> implements ISysUserGroupService {

    @Override
    public PageInfo<SysUserGroup> getPageInfo(PagingRequest pagingRequest, Wrapper<SysUserGroup> queryWrapper) {
      PageHelper.startPage(pagingRequest.getPageNum(),pagingRequest.getPageSize());
      return new PageInfo<>(super.list(queryWrapper));
    }
}
