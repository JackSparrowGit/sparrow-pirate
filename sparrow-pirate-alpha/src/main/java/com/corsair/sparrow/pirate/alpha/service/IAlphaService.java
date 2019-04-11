package com.corsair.sparrow.pirate.alpha.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.corsair.sparrow.pirate.alpha.domain.bean.Alpha;
import com.corsair.sparrow.pirate.core.base.PagingRequest;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * Alpha表 服务类
 * </p>
 *
 * @author jack
 * @since 2019-03-19
 */
public interface IAlphaService extends IService<Alpha> {

    /**
     * 默认带条件参数分页
     * @param pagingRequest
     * @param queryWrapper
     * @return
     */
    PageInfo<Alpha> getPageInfo(PagingRequest pagingRequest, Wrapper<Alpha> queryWrapper);
}
