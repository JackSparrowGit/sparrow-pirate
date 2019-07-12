package com.corsair.sparrow.pirate.conf.log;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jack
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "用户日志对象",description = "记录用户操作日志")
public class SysLog implements Serializable {
    private static final long serialVersionUID = -1426083771681551614L;

    @ApiModelProperty(value = "用户日志:(生成规则, ip + mac + thread + hash)")
    private String id;

    @ApiModelProperty(value = "日志级别")
    private String level;

    @ApiModelProperty(value = "日志标题")
    private String title;

    @ApiModelProperty(value = "操作用户")
    private Long userId;

    @ApiModelProperty(value = "操作租户")
    private Long tenantId;

    @ApiModelProperty(value = "数据操作类型")
    private String opsType;

    @ApiModelProperty(value = "类名")
    private String clazzName;

    @ApiModelProperty(value = "方法名")
    private String methodName;

    @ApiModelProperty(value = "代码行")
    private String codeNo;

    @ApiModelProperty(value = "线程信息")
    private String threadInfo;

    @ApiModelProperty(value = "请求参数")
    private String params;

    @ApiModelProperty(value = "返回结果")
    private String result;

    @ApiModelProperty(value = "处理时长 毫秒")
    private Long handlerTimes;

    @ApiModelProperty(value = "请求时间")
    private Date requestTime;

    @ApiModelProperty(value = "返回时间")
    private Date responseTime;

    @ApiModelProperty(value = "请求uri")
    private Date requestUri;

    @ApiModelProperty(value = "网络状态")
    private Integer status;

    @ApiModelProperty(value = "业务编码")
    private String  respCode;

    @ApiModelProperty(value = "业务信息")
    private String  respMsg;

    @ApiModelProperty(value = "服务端ip")
    private String  serverIp;

    @ApiModelProperty(value = "客户端ip")
    private String  clientIp;

    @ApiModelProperty(value = "备注")
    private String  remark;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
