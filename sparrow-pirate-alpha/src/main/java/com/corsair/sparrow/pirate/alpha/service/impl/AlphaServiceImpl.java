package com.corsair.sparrow.pirate.alpha.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.corsair.sparrow.pirate.alpha.domain.bean.Alpha;
import com.corsair.sparrow.pirate.alpha.mapper.AlphaMapper;
import com.corsair.sparrow.pirate.alpha.service.IAlphaService;
import com.corsair.sparrow.pirate.core.base.PagingRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Alpha表 服务实现类
 * </p>
 *
 * @author jack
 * @since 2019-03-19
 */
@Service
public class AlphaServiceImpl extends ServiceImpl<AlphaMapper, Alpha> implements IAlphaService {

    @Override
    public PageInfo<Alpha> getPageInfo(PagingRequest pagingRequest, Wrapper<Alpha> queryWrapper) {
        PageHelper.startPage(pagingRequest.getPageNum(),pagingRequest.getPageSize());
        return new PageInfo<>(super.list(queryWrapper));
    }
}
