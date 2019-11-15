package com.runer.cibao.web;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.domain.ErrorRecovery;
import com.runer.cibao.domain.User;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.ErrorRecoveryService;
import com.runer.cibao.service.UserLoginService;
import com.runer.cibao.util.NormalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import static com.runer.cibao.exception.ResultMsg.SUCCESS;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/29
 **/
@Controller
@RequestMapping("errorsRecovery")
public class ErrorRecoveryController {


    @Autowired
    ErrorRecoveryService  beanService ;

    @Autowired
    UserLoginService userLoginService ;



    @RequestMapping(value = "index")
    public String schoolIndex(){
        return  "errorRecovery/errors_manage" ;
    }

    @RequestMapping(value = "reply")
    public String addDataIndex(Long id , ModelMap map){
        if (id!=null){
            try {
                map.put("data",beanService.findById(id));
            } catch (SmartCommunityException e) {
                e.printStackTrace();
            }
        }
        return  "errorRecovery/errors_reply" ;
    }

    @RequestMapping(value = "errorDes")
    public String errorDes(Long id , ModelMap map){
        if (id!=null){
            try {
                map.put("data",beanService.findById(id));
            } catch (SmartCommunityException e) {
                e.printStackTrace();
            }
        }
        return  "errorRecovery/error_des" ;
    }



    @RequestMapping(value = "data_list")
    @ResponseBody
    public LayPageResult<ErrorRecovery> getDataList(Long userId , Long replyUserId ,Integer isResolved , Integer page , Integer limit){
        Page<ErrorRecovery> pageResult = beanService.findErrors(userId,replyUserId,isResolved,page,limit) ;
        return NormalUtil.createLayPageReuslt(pageResult) ;
    }


    @RequestMapping(value = "replyData")
    public ApiResult replyForError(Long id , String reply, HttpServletRequest req){
        User currentUser = userLoginService.getCurrentUser(req);
        if (currentUser==null){
            return  new ApiResult(ResultMsg.NOT_FOUND,null) ;
        }
        return    beanService.resolveError(id,currentUser.getId(),reply) ;
    }

    @RequestMapping("deleteByIds")
    public ApiResult deleteByIds(String ids){
        return  NormalUtil.deleteByIds(beanService,ids) ;
    }

    @RequestMapping("deleteById")
    public ApiResult deleteById(Long id){
        return  NormalUtil.deleteById(beanService,id);
    }

}
