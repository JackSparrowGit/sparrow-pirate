package com.corsair.sparrow.pirate.oauth.domain.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableField;
import com.corsair.sparrow.pirate.core.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author jack
 * @since 2019-03-22
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_sys_user")
@ApiModel(value="SysUser对象", description="用户表")
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "密码盐")
    @TableField("salt")
    private String salt;

    @ApiModelProperty(value = "姓")
    @TableField("first_name")
    private String firstName;

    @ApiModelProperty(value = "名")
    @TableField("last_name")
    private String lastName;

    @ApiModelProperty(value = "昵称")
    @TableField("nick_name")
    private String nickName;

    @ApiModelProperty(value = "手机号")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "性别")
    @TableField("gender")
    private String gender;

    @ApiModelProperty(value = "头像")
    @TableField("image")
    private String image;

    @ApiModelProperty(value = "是否可用")
    @TableField("is_enabled")
    private Boolean isEnabled;

    @ApiModelProperty(value = "账号是否过期")
    @TableField("is_account_non_expired")
    private Boolean isAccountNonExpired;

    @ApiModelProperty(value = "账号是否被锁定")
    @TableField("is_account_non_locked")
    private Boolean isAccountNonLocked;

    @ApiModelProperty(value = "账号认证信息是否过期")
    @TableField("is_credentials_non_expired")
    private Boolean isCredentialsNonExpired;

    @ApiModelProperty(value = "语种(en zh_CN ..)")
    @TableField("language")
    private String language;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "籍贯")
    @TableField("region")
    private String region;

    @ApiModelProperty(value = "最后登陆时间")
    @TableField("last_login_time")
    private Date lastLoginTime;

    @ApiModelProperty(value = "业务私钥")
    @TableField("primary_key")
    private String primaryKey;

    @ApiModelProperty(value = "生日")
    @TableField("birthday")
    private Date birthday;


}
