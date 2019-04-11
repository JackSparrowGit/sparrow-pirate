package com.corsair.sparrow.pirate.oauth.service.impl;

import com.corsair.sparrow.pirate.oauth.domain.bean.OauthClientDetails;
import com.corsair.sparrow.pirate.oauth.mapper.OauthClientDetailsMapper;
import com.corsair.sparrow.pirate.oauth.service.IOauthClientDetailsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.corsair.sparrow.pirate.core.base.PagingRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * oauth鉴权表 服务实现类
 * </p>
 *
 * @author jack
 * @since 2019-03-22
 */
@Service
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails> implements IOauthClientDetailsService {

    @Override
    public PageInfo<OauthClientDetails> getPageInfo(PagingRequest pagingRequest, Wrapper<OauthClientDetails> queryWrapper) {
      PageHelper.startPage(pagingRequest.getPageNum(),pagingRequest.getPageSize());
      return new PageInfo<>(super.list(queryWrapper));
    }
}
