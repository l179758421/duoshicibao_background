package com.runer.cibao.service;

import com.runer.cibao.domain.ImageInDbForCache;
import com.runer.cibao.domain.repository.ImageInDbForCacheRepository;

import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/12
 **/
public interface ImageInDbForCacheService extends BaseService<ImageInDbForCache, ImageInDbForCacheRepository>{

   List<ImageInDbForCache> findByFeedbackId(Long feedId);
}
