package com.corsair.sparrow.pirate.core.base;

import com.corsair.sparrow.pirate.core.constant.GlobalConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jack
 */
@Data
public class PagingRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "开始页数",name = "pageNum")
    private Integer pageNum;
    @ApiModelProperty(value = "页Size",name = "pageSize")
    private Integer pageSize;

    public PagingRequest() {
        this(GlobalConstant.DEFAULT_PAGE_NUM,GlobalConstant.DEFAULT_PAGE_SIZE);
    }

    public PagingRequest(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }


}
