package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.AdminLevel;
import com.runer.cibao.domain.repository.AdminLevelRepository;

public interface AdminLevelService extends BaseService<AdminLevel,AdminLevelRepository> {
    /**
     * 获取级别
     * @param appUserId
     * @return
     */
    ApiResult getLevel(Long appUserId);

    /**
     * 保存或更新级别说明
     * @param id
     * @param levelRule
     * @param levelUse
     * @return
     */
    ApiResult addOrUpdateLevel(Long id, String levelRule, String levelUse);
}
