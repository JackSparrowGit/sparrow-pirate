package com.corsair.sparrow.pirate.oauth.mapper;

import com.corsair.sparrow.pirate.oauth.domain.bean.SysUserGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户-用户组关联表 Mapper 接口
 * </p>
 *
 * @author jack
 * @since 2019-03-22
 */
 @Mapper
public interface SysUserGroupMapper extends BaseMapper<SysUserGroup> {

}
