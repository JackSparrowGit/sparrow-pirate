package com.corsair.sparrow.pirate.gamma.domain.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author jack
 */
@Data
@Entity
@Table(name = "t_gamma")
public class Gamma implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(name = "name",columnDefinition = "名称")
    private String name;
//    @Column(name = "create_time",columnDefinition = "timestamp default current_timestamp comment '创建时间'")
//    private Date createTime;
//    @Column(name = "update_time",columnDefinition = "timestamp default current_timestamp comment '修改时间'",updatable = true)
//    private Date updateTime;

}
