package com.runer.cibao.web;

import com.runer.cibao.Config;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.Base;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.domain.ClassInSchool;
import com.runer.cibao.domain.Member;
import com.runer.cibao.domain.Message;
import com.runer.cibao.domain.User;
import com.runer.cibao.service.AdminMessageService;
import com.runer.cibao.service.ClassInSchoolService;
import com.runer.cibao.service.MessageService;
import com.runer.cibao.service.UserLoginService;
import com.runer.cibao.util.NormalUtil;
import com.runer.cibao.util.PowerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author sww
 * @Date 2018/7/7
 *
 **/

@RestController
@RequestMapping(value = "message")
public class MessageController  extends Base {


    @Autowired
    MessageService beanService ;
    @Autowired
    UserLoginService userLoginService ;
    @Autowired
    AdminMessageService adminMessageService ;
    @Autowired
    ClassInSchoolService classInSchoolService ;


    @RequestMapping(value = "data_list")
    public LayPageResult<Message> getDataList(Long userId ,String content ,Integer type ,    Integer state ,Integer page ,Integer limit){
        Page<Message> adminMessages = beanService.findAdminMessages(userId,state , type, content,null, page, limit);
        return  NormalUtil.createLayPageReuslt(adminMessages);
    }

    @RequestMapping(value = "class_data_list")
    public LayPageResult<Message> getClassDataList(String content,Integer state, Integer page ,Integer limit,HttpServletRequest req){
        User currentUser = userLoginService.getCurrentUser(req);
        if(currentUser==null){

        }
        System.out.println(currentUser.getId());
        System.out.println(Config.CLASS_MSG);
        System.out.println(Config.CLASS_MSG);
        Page<Message> classMessage =beanService.findAdminMessages(currentUser.getId() ,state, Config.CLASS_MSG,content, null,  page,limit);
        return  NormalUtil.createLayPageReuslt(classMessage) ;
    }
    @RequestMapping(value = "sendSysMessage")
    public ApiResult sendSysMessage(String msgContent , String title , String url , Long sendUserId, HttpServletRequest req){
        User user =userLoginService.getCurrentUser(req);
        if (user!=null){
            return beanService.generateSystemMessage(msgContent,title,url, Config.SYSTEM_MSG,null,user.getId());
        }else{
            return  new ApiResult("用户id不能为空");
        }
    }
    @RequestMapping(value = "sendClassMessage")
    public ApiResult sendClassMessage(String msgContent ,String title ,String url ,Long classInschoolId  ,Long sendUserId,HttpServletRequest req){

        User user = userLoginService.getCurrentUser(req);
        if (user!=null){
            return  beanService.generateClassMessage(msgContent,title,url, Config.CLASS_MSG,null,classInschoolId,sendUserId);
        }else{
            return  new ApiResult("用户id不能为空");
        }

    }

    @RequestMapping("deleteByIds")
    public ApiResult deleteByIds(String ids){
        return  NormalUtil.deleteByIds(adminMessageService,ids) ;
    }


    @RequestMapping("deleteById")
    public ApiResult deleteById(Long id){
        return  NormalUtil.deleteById(adminMessageService,id);
    }





}
