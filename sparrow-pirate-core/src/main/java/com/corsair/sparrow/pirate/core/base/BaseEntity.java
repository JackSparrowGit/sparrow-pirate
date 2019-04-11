package com.corsair.sparrow.pirate.core.base;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author jack
 */
@Getter
@Setter
public abstract class BaseEntity<T extends Model> extends Model<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键Id
     */
    @TableId
    private Long id;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
