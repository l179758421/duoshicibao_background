package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.TestRule;
import com.runer.cibao.domain.repository.TestRuleRepository;

public interface TestRuleService extends BaseService<TestRule,TestRuleRepository> {

    /**
     * 查找测试规则
     * @return
     */
    ApiResult findRules();

    void addRule(Long id, String content);
}
