package com.corsair.sparrow.pirate.gamma.repository;

import com.corsair.sparrow.pirate.gamma.domain.bean.Gamma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jack
 */
@Repository
public interface GammaRepository extends JpaRepository<Gamma,Long> {

    /**
     * 根据id或者名称查找gamma list
     * @param id
     * @param name
     * @return
     */
    List<Gamma> findListByIdOrName(Long id, String name);
}
