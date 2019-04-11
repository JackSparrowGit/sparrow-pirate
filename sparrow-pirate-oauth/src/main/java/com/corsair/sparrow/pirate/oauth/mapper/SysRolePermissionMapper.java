package com.corsair.sparrow.pirate.oauth.mapper;

import com.corsair.sparrow.pirate.oauth.domain.bean.SysRolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色权限关联表 Mapper 接口
 * </p>
 *
 * @author jack
 * @since 2019-03-22
 */
 @Mapper
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {

}
