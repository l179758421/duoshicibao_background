package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.AdminLevel;
import com.runer.cibao.domain.AppUser;
import com.runer.cibao.domain.repository.AdminLevelRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.AdminLevelService;
import com.runer.cibao.service.AppUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminLevelServiceImpl extends BaseServiceImp<AdminLevel, AdminLevelRepository> implements AdminLevelService {

    @Autowired
    AppUserService appUserService;

    @Override
    public ApiResult getLevel(Long appUserId) {
        Map<String, String> levelMap = new HashMap<String, String>();

        ApiResult userResult = appUserService.findByIdWithApiResult(appUserId);
        if (userResult.isFailed()) {
            return new ApiResult("用户id为空");
        }
        AppUser appUser = (AppUser) userResult.getData();
        List<AdminLevel> list = findAll();
        if (!ListUtils.isEmpty(list)) {
            AdminLevel levelDes = list.get(0);
            levelMap.put("rule", levelDes.getLevelRule());
            levelMap.put("use", levelDes.getLevelUse());
        }
        levelMap.put("level", appUser.getLevel());
        return new ApiResult(ResultMsg.SUCCESS, levelMap);
    }

    @Override
    public ApiResult addOrUpdateLevel(Long id, String levelRule, String levelUse) {
        AdminLevel level = new AdminLevel();
        if (id != null) {
          ApiResult apiResult =   findByIdWithApiResult(id) ;
          if (apiResult.isFailed()){
              return  apiResult ;
          }
          level = (AdminLevel) apiResult.getData();
        }else{
            if (!ListUtils.isEmpty(findAll())){
                return  new ApiResult("不能添加两个规则用处说明！");
            }
        }
        if(StringUtils.isNotEmpty(levelRule)){
            level.setLevelRule(levelRule);
        }
        if(StringUtils.isNotEmpty(levelUse)){
            level.setLevelUse(levelUse);
        }
        r.save(level);
       return new ApiResult(ResultMsg.SUCCESS,level);
    }


}

