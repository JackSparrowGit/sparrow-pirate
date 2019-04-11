package com.corsair.sparrow.pirate.oauth.service.impl;

import com.corsair.sparrow.pirate.oauth.domain.bean.SysGroup;
import com.corsair.sparrow.pirate.oauth.mapper.SysGroupMapper;
import com.corsair.sparrow.pirate.oauth.service.ISysGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.corsair.sparrow.pirate.core.base.PagingRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 用户组表 服务实现类
 * </p>
 *
 * @author jack
 * @since 2019-03-22
 */
@Service
public class SysGroupServiceImpl extends ServiceImpl<SysGroupMapper, SysGroup> implements ISysGroupService {

    @Override
    public PageInfo<SysGroup> getPageInfo(PagingRequest pagingRequest, Wrapper<SysGroup> queryWrapper) {
      PageHelper.startPage(pagingRequest.getPageNum(),pagingRequest.getPageSize());
      return new PageInfo<>(super.list(queryWrapper));
    }
}
