package com.corsair.sparrow.pirate.alpha.domain.bean;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.corsair.sparrow.pirate.core.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * Alpha表
 * </p>
 *
 * @author jack
 * @since 2019-03-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_alpha")
@ApiModel(value="Alpha对象", description="Alpha表")
public class Alpha extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "概率")
    @TableField("rate")
    private Double rate;

    @ApiModelProperty(value = "金钱")
    @TableField("money")
    private BigDecimal money;

    @ApiModelProperty(value = "是否删除")
    @TableField("del_flag")
    private Boolean delFlag;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("update_time")
    private Date updateTime;


}
