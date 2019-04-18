package com.corsair.sparrow.pirate.gamma.service.impl;

import com.corsair.sparrow.pirate.gamma.api.GammaDTO;
import com.corsair.sparrow.pirate.gamma.api.GammaQuery;
import com.corsair.sparrow.pirate.gamma.api.GammaThriftService;
import com.corsair.sparrow.pirate.gamma.domain.bean.Gamma;
import com.corsair.sparrow.pirate.gamma.repository.GammaRepository;
import com.google.common.collect.Lists;
import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author jack
 */
@Service
public class GammaThriftServiceImpl implements GammaThriftService.Iface{

    @Autowired
    private GammaRepository gammaRepository;

    @Override
    public GammaDTO getById(long id) throws TException {
        Gamma gamma = gammaRepository.getOne(id);
        GammaDTO gammaDTO = new GammaDTO();
        BeanUtils.copyProperties(gamma,gammaDTO);
        return gammaDTO;
    }

    @Override
    public List<GammaDTO> search(GammaQuery query) throws TException {
        List<Gamma> gammaList = gammaRepository.findListByIdOrName(query.getId(),query.getName());
        List<GammaDTO> gammaDTOList = Lists.newArrayList();
        if(Objects.nonNull(gammaList)){
            gammaList.forEach(gamma -> {
                GammaDTO gammaDTO = new GammaDTO();
                BeanUtils.copyProperties(gamma,gammaDTO);
                gammaDTOList.add(gammaDTO);
            });
        }
        return gammaDTOList;
    }
}
