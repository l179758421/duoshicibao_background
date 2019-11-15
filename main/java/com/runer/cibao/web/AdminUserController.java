package com.runer.cibao.web;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.Member;
import com.runer.cibao.domain.Roles;
import com.runer.cibao.domain.User;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.RolesService;
import com.runer.cibao.service.UserLoginService;
import com.runer.cibao.service.UserService;
import com.runer.cibao.util.PowerUtil;
import com.runer.cibao.util.machine.IdsMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.List;

/**
 * @Author sww
 * @Date 2019/9/25
 **/
@RestController
@RequestMapping(value = "adminUI")
@CrossOrigin
public class AdminUserController {

    @Autowired
    UserLoginService userLoginService ;

    @Autowired
    UserService userService ;

    @Autowired
    RolesService rolesService ;

    @RequestMapping(value = "getUser")
    public ApiResult getUser(HttpServletRequest request){
        User user = userLoginService.getCurrentUser(request);
        if(user==null){
            return new ApiResult(ResultMsg.ENTITY_ID_NOT_EXISTS,null);
        }
        String rolesIds = user.getRolesIds() ;
        if (!StringUtils.isEmpty(rolesIds)){
            try {
                String ids = new IdsMachine().deparseIdsToNormal(rolesIds);
                List<Roles> roles = rolesService.findByIds(ids);
                user.setRoles(roles);
            } catch (SmartCommunityException e) {
                return new ApiResult(ResultMsg.ENTITY_ID_NOT_EXISTS,null);
            }
        }
        return  new ApiResult(ResultMsg.SUCCESS,user);
    }

    @RequestMapping(value = "resetPassword")
    public ApiResult resetPassword(Long userId ,String loginName ,String loginPass){
        if(StringUtils.isEmpty(loginName)){
            return new ApiResult("请输入登录账号");
        }
        if(StringUtils.isEmpty(loginPass)){
            return new ApiResult("请输入密码");
        }
        return   userLoginService.resetPassword(loginName,loginPass) ;
    }


    @RequestMapping(value = "changeUserPass")
    public ApiResult changeUserPass(Long userId ,String pass ,String loginName,String originPass ){
        return   userLoginService.changeUserPass(userId,loginName,pass ,originPass) ;
    }
}
