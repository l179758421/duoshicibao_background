package com.runer.cibao.service.impl;

import com.runer.cibao.domain.ImageInDbForCache;
import com.runer.cibao.domain.repository.ImageInDbForCacheRepository;
import com.runer.cibao.service.ImageInDbForCacheService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/12
 **/
@Service
public class ImageInDbForCacheServiceImpl extends BaseServiceImp<ImageInDbForCache,ImageInDbForCacheRepository> implements ImageInDbForCacheService {
    @Override
    public List<ImageInDbForCache> findByFeedbackId(Long feedId) {
        return r.findByFeedBack_Id(feedId);
    }
}
