package com.runer.cibao.web;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.domain.HelpToUser;
import com.runer.cibao.domain.User;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.HelpToUserService;
import com.runer.cibao.service.UserLoginService;
import com.runer.cibao.util.NormalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author sww
 * @Date 2019/9/24
 **/
@RestController
@RequestMapping(value = "help")
public class HelpeToUserController {

    @Autowired
    HelpToUserService beanService ;

    @Autowired
    UserLoginService userLoginService ;





    @RequestMapping(value = "data_list")
    public LayPageResult<HelpToUser> getDataList(String theme, Integer page , Integer limit){

        Page<HelpToUser> pageResult = beanService.findHelpToUsers(theme, page, limit);

        return NormalUtil.createLayPageReuslt(pageResult) ;
    }
    /**
     * (Long id ,String theme ,String content ,Long userId);
     * @return
     */
    @RequestMapping(value = "addOrUpdateData")
    public ApiResult addOrUpdateData(Long id , String theme , String content, String subContent, HttpServletRequest req){
        User currentUser = userLoginService.getCurrentUser(req);
        if (currentUser==null){
            return  new ApiResult(ResultMsg.USER_ID_IS_NOT_ALLOWED_NULL,null) ;
        }
        return  beanService.addOrUpdateHelptoUser(id,theme,content,currentUser.getId(),subContent);
    }

    @RequestMapping("deleteByIds")
    public ApiResult deleteByIds(String ids){
        return  NormalUtil.deleteByIds(beanService,ids) ;
    }

    @RequestMapping("deleteById")
    public ApiResult deleteById(Long id){
        return  NormalUtil.deleteById(beanService,id);
    }

    @RequestMapping("getDetail")
    public ApiResult getHelpDetail(Long id ){
        try {
            HelpToUser helpToUser =beanService.findById(id);
            return  new ApiResult(ResultMsg.SUCCESS,helpToUser) ;
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return  new ApiResult(e.getResultMsg(),null) ;
        }
    }
}
