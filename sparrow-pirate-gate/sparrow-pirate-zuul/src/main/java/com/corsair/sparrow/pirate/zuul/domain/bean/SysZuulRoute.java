package com.corsair.sparrow.pirate.zuul.domain.bean;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 路由表
 * </p>
 *
 * @author jack
 * @since 2019-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysZuulRoute对象", description="路由表")
public class SysZuulRoute implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "路由id")
    private String id;

    @ApiModelProperty(value = "路由path")
    private String path;

    @ApiModelProperty(value = "路由serviceId")
    private String serviceId;

    @ApiModelProperty(value = "路由url")
    private String url;

    @ApiModelProperty(value = "请求敏感头")
    private String sensitiveHeaders;

    @ApiModelProperty(value = "是否跳过请求前缀")
    private Boolean stripPrefix;

    @ApiModelProperty(value = "是否重试")
    private Boolean retryable;

    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;


}
