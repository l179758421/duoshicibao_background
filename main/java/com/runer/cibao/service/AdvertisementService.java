package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.Advertisement;
import com.runer.cibao.domain.repository.AdvertisementRepository;
import org.springframework.data.domain.Page;

import java.util.Date;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/23
 **/
public interface AdvertisementService  extends BaseService<Advertisement, AdvertisementRepository>{


      ApiResult findAllAds(String title, Integer type, boolean containsOutDate);

      Page<Advertisement> findAllAds2(String title, Integer type, boolean containsOutDate, Integer page, Integer limit);

      ApiResult addOrUpdateAd(Long id, String title, String url, Long relatedId, Integer type, Integer orderNum, String imgUrl, Date startDate, Date endDate);

      Advertisement getBiggestOrderNum() ;

      Advertisement getSmallerOrderNum();

      ApiResult setOneToBiggest(Long id) ;


      ApiResult getById(Long id);
}
