package com.runer.cibao.util;

import com.runer.cibao.Config;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.Member;
import com.runer.cibao.domain.Roles;
import com.runer.cibao.domain.User;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.BaseService;
import com.runer.cibao.service.RolesService;
import com.runer.cibao.service.UserLoginService;
import com.runer.cibao.service.UserService;
import org.springframework.ui.ModelMap;

import java.util.Date;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/7/3
 **/
public class PowerUtil {


    /**
     * 生成特定的角色用户；
     * @param userService
     * @param rolesService
     * @param phone
     * @param roleName
     * @param encoder
     * @return
     */
    public static ApiResult generateRolesUser(UserService userService , RolesService rolesService ,
                                              String phone , String name , String roleName , Encoder encoder   ){
        User user =null ;
        //关联user 设置密码；
            //loginName存在的情况下
            if (userService.findUserByLoginName(phone)!=null){
                return new ApiResult("该手机号已存在!");
            }
            user =new User();
            user.setLoginName(phone);
            user.setName(name);
            user.setPassWord(encoder.passwordEncoderByMd5(Config.DEFAULT_PASS));
            user.setCreateTiem(new Date());
            Roles roles = rolesService.findByRolesName(roleName) ;
            user.setRolesIds(","+roles.getId());
            try {
                user =  userService.saveOrUpdate(user) ;
                return  new ApiResult(ResultMsg.SUCCESS,user) ;
            } catch (SmartCommunityException e) {
                e.printStackTrace();
                return  new ApiResult(ResultMsg.OS_ERROR,null);
            }

    }



    public  interface  RightPowerCallBack{
        void success(Member member) ;
    }


    public static String agentsCheck(UserLoginService userLoginService ,ModelMap modelMap ,RightPowerCallBack rightCallBack){
        ApiResult memberResult = userLoginService.getCurrentMember();
        if (memberResult.getMsgCode()!= ResultMsg.SUCCESS.getMsgCode()){
            modelMap.put("error",memberResult.getMsg());
            return  "error" ;
        }
        Member member = (Member) memberResult.getData();
        if (!member.isAgents()){
            modelMap.put("error","暂无权限操作！");
            return  "error";
        }
        rightCallBack.success(member);
        return  null ;
    }



    public static  String adminCheck(UserLoginService userLoginService ,ModelMap modelMap ,RightPowerCallBack rightCallBack){
        ApiResult memberResult = userLoginService.getCurrentMember();
        if (memberResult.getMsgCode()!= ResultMsg.SUCCESS.getMsgCode()){
            modelMap.put("error",memberResult.getMsg());
            return  "error" ;
        }
        Member member = (Member) memberResult.getData();
        if (!member.isAdmin()){
            modelMap.put("error","暂无权限操作！");
            return  "error";
        }
        rightCallBack.success(member);
        return  null ;
    }

    public static  String superAdminCheck(UserLoginService userLoginService ,ModelMap modelMap ,RightPowerCallBack rightCallBack){
        ApiResult memberResult = userLoginService.getCurrentMember();
        if (memberResult.getMsgCode()!= ResultMsg.SUCCESS.getMsgCode()){
            modelMap.put("error",memberResult.getMsg());
            return  "error" ;
        }
        Member member = (Member) memberResult.getData();
        if (!member.isSuperAdmin()){
            modelMap.put("error","暂无权限操作！");
            return  "error";
        }
        rightCallBack.success(member);
        return  null ;
    }



    public static  String teachePowerCheck(UserLoginService userLoginService , ModelMap  modelMap ,
                                           RightPowerCallBack rightPowerCallBack){
        ApiResult memberResult = userLoginService.getCurrentMember();
        if (memberResult.getMsgCode()!= ResultMsg.SUCCESS.getMsgCode()){
            modelMap.put("error",memberResult.getMsg());
            return  "error" ;
        }
        Member member = (Member) memberResult.getData();
        if (member.getTeacher()==null){
            modelMap.put("error","暂无权限操作！");
            return  "error";
        }
        rightPowerCallBack.success(member);
        return  null ;
    }




    public static  String  schoolMasterPower(UserLoginService userLoginService , ModelMap  modelMap ,RightPowerCallBack rightPowerCallBack){
        ApiResult memberResult = userLoginService.getCurrentMember();
        if (memberResult.getMsgCode()!= ResultMsg.SUCCESS.getMsgCode()){
            modelMap.put("error",memberResult.getMsg());
            return  "error" ;
        }
        Member member = (Member) memberResult.getData();
        if (member.getSchoolMaster()==null){
            modelMap.put("error","暂无权限操作！");
            return  "error";
        }
        rightPowerCallBack.success(member);
        return  null ;
    }



    public interface  RightCallBack{
        void success(ApiResult apiResult, ModelMap modelMap);
        void error(ApiResult apiResult, ModelMap modelMap);
    }


    public static String handleAddOrUpdateIndex(Long id , BaseService beanService , ModelMap map ){
        if (id!=null) {
            ApiResult apiResult = beanService.findByIdWithApiResult(id);
            if (apiResult.getMsgCode() != ResultMsg.SUCCESS.getMsgCode()) {
                map.put("error", apiResult.getMsg());
                return "error";
            }
            map.put("data", apiResult.getData());
        }
        return  null ;
    }

    public static String handleAddOrUpdateIndexWithCallBack(Long id , BaseService beanService , ModelMap map ,RightCallBack rightCallBack ){
        if (id!=null) {
            ApiResult apiResult = beanService.findByIdWithApiResult(id);
            if (apiResult.getMsgCode() != ResultMsg.SUCCESS.getMsgCode()) {
                map.put("error", apiResult.getMsg());
                rightCallBack.error(apiResult,map);
                return "error";
            }
            map.put("data", apiResult.getData());
            rightCallBack.success(apiResult,map);
        }
        ApiResult apiResult =new ApiResult(ResultMsg.SUCCESS,null) ;
        rightCallBack.success(apiResult,map);
        return  null ;

    }


    public  static String checkUserLogin(UserLoginService userLoginService ,ModelMap map ,RightPowerCallBack rightCallBack){
        ApiResult userResult =userLoginService.getCurrentMember() ;
        if (userResult.isFailed()){
            map.put("error",userResult.getMsg()) ;
            return  "error" ;
        }
        Member member = (Member) userResult.getData();
        rightCallBack.success(member);
        return  null ;
    }



}
