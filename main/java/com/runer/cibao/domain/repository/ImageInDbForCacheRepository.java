package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.ImageInDbForCache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Author szhua
 * @Description:SmartCommunity==
 * @Date 2018/5/31
 *
 **/
public interface ImageInDbForCacheRepository  extends JpaRepository<ImageInDbForCache,Long> ,JpaSpecificationExecutor<ImageInDbForCache> {
     List<ImageInDbForCache> findByFeedBack_Id(Long feedId);
}
