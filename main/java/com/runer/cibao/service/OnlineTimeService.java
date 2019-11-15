package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.OnlineTime;
import com.runer.cibao.domain.repository.OnlineTimeRepository;

import java.util.Date;
import java.util.List;

public interface OnlineTimeService extends BaseService<OnlineTime, OnlineTimeRepository> {
    /**
     * 获得用户在线时长
     * @param userId
     * @return
     */
    ApiResult getOnlineTime(Long userId);

    List<OnlineTime> findByUserId(Long userId);

    List<OnlineTime> findByUserAndDate(Long userId, Date date);

    List<OnlineTime> getByUserId(Long userId);
}
