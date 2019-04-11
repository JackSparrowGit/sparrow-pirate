package com.corsair.sparrow.pirate.oauth.domain.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableField;
import com.corsair.sparrow.pirate.core.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author jack
 * @since 2019-03-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_sys_permission")
@ApiModel(value="SysPermission对象", description="权限表")
public class SysPermission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "父节点ID")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty(value = "父节点ID集合，逗号分隔")
    @TableField("parent_ids")
    private String parentIds;

    @ApiModelProperty(value = "权限名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "权限深度")
    @TableField("depth")
    private Integer depth;

    @ApiModelProperty(value = "排列顺序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "前端icon")
    @TableField("icon")
    private String icon;

    @ApiModelProperty(value = "前端class")
    @TableField("clazz")
    private String clazz;

    @ApiModelProperty(value = "前端style")
    @TableField("style")
    private String style;

    @ApiModelProperty(value = "资源url")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "资源类型")
    @TableField("type")
    private String type;

    @ApiModelProperty(value = "支持的请求方式 *代表所有")
    @TableField("methods")
    private String methods;

    @ApiModelProperty(value = "参数域")
    @TableField("options_fields")
    private String optionsFields;

    @ApiModelProperty(value = "保护类型")
    @TableField("protect_type")
    private String protectType;

    @ApiModelProperty(value = "创建者")
    @TableField("create_by")
    private String createBy;

    @ApiModelProperty(value = "修改者")
    @TableField("update_by")
    private String updateBy;

    @ApiModelProperty(value = "是否可用")
    @TableField("is_enable")
    private Boolean isEnable;

    @ApiModelProperty(value = "描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("update_time")
    private Date updateTime;


}
