package com.corsair.sparrow.pirate.gamma.web.rest;

import com.corsair.sparrow.pirate.gamma.api.GammaDTO;
import com.corsair.sparrow.pirate.gamma.api.GammaThriftService;
import com.corsair.sparrow.pirate.gamma.config.ThriftReference;
import com.corsair.sparrow.pirate.gamma.domain.vo.GammaVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jack
 */
@Slf4j
@RestController
@RequestMapping()
public class GammaRestController {

    @ThriftReference(version = "1.0.1")
    private GammaThriftService.Iface gammaThriftService;

    @GetMapping(value = "/gamma")
    public GammaVO gamma() throws TException {
        GammaVO gammaVo = new GammaVO();
        GammaDTO gammaDTO = gammaThriftService.getById(1L);
        log.info("gammaDTO:{}",gammaDTO);
        BeanUtils.copyProperties(gammaDTO,gammaVo);
        return gammaVo;
    }

}
