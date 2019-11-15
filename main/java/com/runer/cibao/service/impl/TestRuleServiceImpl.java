package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.TestRule;
import com.runer.cibao.domain.repository.TestRuleRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.TestRuleService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TestRuleServiceImpl extends BaseServiceImp<TestRule, TestRuleRepository> implements TestRuleService {
    @Override
    public ApiResult findRules() {
        List<TestRule> list = findAll();
        return new ApiResult(ResultMsg.SUCCESS,list);
    }

    @Override
    public void addRule(Long id,String content) {
        TestRule testRule=null;
        if(StringUtils.isEmpty(id)){
               testRule=new TestRule();
               testRule.setRule(content);
        }else{
            try {
                testRule = findById(id);
                testRule.setRule(content);
            } catch (SmartCommunityException e) {
                e.printStackTrace();
            }
        }
        try {
            saveOrUpdate(testRule);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }

    }
}
