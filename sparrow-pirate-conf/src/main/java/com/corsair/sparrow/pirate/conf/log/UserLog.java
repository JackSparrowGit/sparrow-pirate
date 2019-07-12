package com.corsair.sparrow.pirate.conf.log;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户日志
 * @author jack
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "用户日志对象",description = "记录用户操作日志")
public class UserLog implements Serializable {
    private static final long serialVersionUID = 1311814338137654156L;

    @ApiModelProperty(value = "用户日志:(生成规则, ip + mac + thread + hash)")
    private String id;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "索引")
    private String index;
    @ApiModelProperty(value = "用户ID")
    private Long userId;
    @ApiModelProperty(value = "租户id")
    private Long tenantId;
    @ApiModelProperty(value = "请求参数")
    private String params;
    @ApiModelProperty(value = "异常信息")
    private String exceptions;
    @ApiModelProperty(value = "处理耗时")
    private Long handlerTimes;
    @ApiModelProperty(value = "请求时间")
    private Date requestTime;
    @ApiModelProperty(value = "响应时间")
    private Date responseTime;
    @ApiModelProperty(value = "请求方式")
    private String requestMethod;
    @ApiModelProperty(value = "请求uri")
    private String requestUri;
    @ApiModelProperty(value = "业务编码")
    private String respCode;
    @ApiModelProperty(value = "业务信息")
    private String respMsg;
    @ApiModelProperty(value = "实例id")
    private String serviceId;
    @ApiModelProperty(value = "服务端ip")
    private String serverIp;
    @ApiModelProperty(value = "客户端ip")
    private String clientIp;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "token")
    private String token;
    @ApiModelProperty(value = "用户代理")
    private String agent;

    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
