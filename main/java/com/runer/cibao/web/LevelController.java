package com.runer.cibao.web;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.domain.AdminLevel;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.AdminLevelService;
import com.runer.cibao.util.NormalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "levelDes")
public class LevelController {

    @Autowired
    AdminLevelService adminLevelService;

    @RequestMapping(value = "level_list")
    public LayPageResult<AdminLevel> getLevel(Integer page, Integer limit) {
        Page<AdminLevel> levelPage = adminLevelService.findByPage(page, limit);
        return NormalUtil.createLayPageReuslt(levelPage) ;
    }



    @RequestMapping(value = "addOrUpdateLevel")
    ApiResult addOrUpdateLevel(Long id, String levelRule, String levelUse) {
        return adminLevelService.addOrUpdateLevel(id, levelRule, levelUse);
    }

    @RequestMapping("deleteById")
    public ApiResult deleteById(Long id) {
        return NormalUtil.deleteById(adminLevelService, id);
    }

}
