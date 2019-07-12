package com.corsair.sparrow.pirate.conf.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "用户对象临时对象",description = "用户对象临时对象")
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 8565382479234487741L;

    @ApiModelProperty(value = "用户Id")
    private Long userId;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "租户id")
    private Long tenantId;

    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "用户组id")
    private String groupId;

    @ApiModelProperty(value = "是否为管理员")
    private Boolean isAdmin;
}
