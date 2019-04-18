package com.corsair.sparrow.pirate.gamma.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jack
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GammaVO implements Serializable {
    private static final long serialVersionUID = 4292805237898160728L;

    private Long id;
    private String name;
}
