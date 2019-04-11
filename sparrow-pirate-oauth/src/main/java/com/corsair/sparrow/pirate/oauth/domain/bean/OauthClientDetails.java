package com.corsair.sparrow.pirate.oauth.domain.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.corsair.sparrow.pirate.core.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * oauth鉴权表
 * </p>
 *
 * @author jack
 * @since 2019-03-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_oauth_client_details")
@ApiModel(value="OauthClientDetails对象", description="oauth鉴权表")
public class OauthClientDetails extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设备id")
    @TableId("client_id")
    private String clientId;

    @ApiModelProperty(value = "资源ids")
    @TableField("resource_ids")
    private String resourceIds;

    @ApiModelProperty(value = "设备密钥")
    @TableField("client_secret")
    private String clientSecret;

    @ApiModelProperty(value = "范围")
    @TableField("scope")
    private String scope;

    @ApiModelProperty(value = "授权类型")
    @TableField("authorized_grant_types")
    private String authorizedGrantTypes;

    @ApiModelProperty(value = "网页重定向uri")
    @TableField("web_server_redirect_uri")
    private String webServerRedirectUri;

    @ApiModelProperty(value = "权限")
    @TableField("authorities")
    private String authorities;

    @ApiModelProperty(value = "access_token时效")
    @TableField("access_token_validity")
    private Integer accessTokenValidity;

    @ApiModelProperty(value = "refresh_token时效")
    @TableField("refresh_token_validity")
    private Integer refreshTokenValidity;

    @ApiModelProperty(value = "附加信息")
    @TableField("additional_information")
    private String additionalInformation;

    @ApiModelProperty(value = "自动授权")
    @TableField("autoapprove")
    private String autoapprove;


}
