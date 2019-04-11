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
 * 用户组表
 * </p>
 *
 * @author jack
 * @since 2019-03-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_sys_group")
@ApiModel(value="SysGroup对象", description="用户组表")
public class SysGroup extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "父节点ID")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty(value = "父节点IDS")
    @TableField("parent_ids")
    private String parentIds;

    @ApiModelProperty(value = "组编号")
    @TableField("group_code")
    private String groupCode;

    @ApiModelProperty(value = "组名称")
    @TableField("group_name")
    private String groupName;

    @ApiModelProperty(value = "组类型")
    @TableField("type")
    private String type;

    @ApiModelProperty(value = "是否可用")
    @TableField("is_enabled")
    private Boolean isEnabled;

    @ApiModelProperty(value = "描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "创建者")
    @TableField("create_by")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("update_time")
    private Date updateTime;


}
