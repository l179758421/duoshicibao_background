package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.UserDao;
import com.runer.cibao.domain.Member;
import com.runer.cibao.domain.User;
import com.runer.cibao.domain.repository.UserRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.UserLoginService;
import com.runer.cibao.service.UserService;
import com.runer.cibao.util.CookieUtil;
import com.runer.cibao.util.Encoder;
import com.runer.cibao.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.runer.cibao.exception.ResultMsg.*;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/4
 * 用户登录service ；
 *
 **/
@Service
public class UserLoginServiceImpl implements UserLoginService {


    @Autowired
    UserDao userDao ;

    @Autowired
    UserRepository userRepository;
    @Autowired
    Encoder encoder ;
    @Autowired
    HttpServletRequest request ;
    @Autowired
    UserService userService ;


    @Autowired
    JwtUtil JwtUtil ;


    @Override
    public ApiResult checkLoginUser(String userName, String pass) {

        if (StringUtils.isEmpty(userName)){
            return new ApiResult(ResultMsg.USERNAME_IS_NULL,null );
        }
        if (StringUtils.isEmpty(pass)){
            return  new ApiResult(ResultMsg.PASSWORD_IS_NULL,null);
        }
        User user =userDao.userExist(userName);
        if (user==null){
            return  new ApiResult(ResultMsg.USER_IS_NOT_EXIST,null);
        }
        String passExists =user.getPassWord() ;
        String passNow =encoder.passwordEncoderByMd5(pass);
        if (!passNow.equals(passExists)){
            return  new ApiResult(ResultMsg.PASSWORID_IS_ILLEGEAL,null);
        }
        return new ApiResult(ResultMsg.SUCCESS,user);
    }

    @Override
    public User existUser(String userName) {
        if (StringUtils.isEmpty(userName)){
            return null;
        }
        return userDao.userExist(userName);
    }



    private void setuserInfo(User user ){
        Member member =new Member() ;
        member.setLoginName(user.getLoginName());
        member.setLoginTime(new Date());
        member.setUserID(user.getId());
        member.setUserName(user.getLoginName());
        member.setSchoolMaster(user.getSchoolMaster());
        member.setTeacher(user.getTeacher());
        member.setAdmin(user.getAdmin());
        member.setAgents(user.getAgents());
        member.setUser(user);
        request.getSession().setAttribute("user",user);

    }


    @Override
    public ApiResult login(String userName, String pass) {
       ApiResult apiResult = checkLoginUser(userName,pass);
       //登录成功的情况下
       if (apiResult.isSuccess()){
           User user = (User) apiResult.getData();
              setuserInfo(user);
       }
           return  apiResult ;
    }

    @Override
    public void inintAdmin() {
        if (userDao.findUserByLoginName("admin")==null){
            User user =new User() ;
            user.setCreateTiem(new Date());
            user.setName("admin");
            user.setLoginName("admin");
            user.setPassWord(encoder.passwordEncoderByMd5("123456"));
            userRepository.saveAndFlush(user);
        }
    }

    @Override
    public ApiResult loginWithRemeberPass(String username, String password, Integer flag ,  HttpServletResponse response)   {
         if (flag==null||flag==0){
             //清除用户登录的信息；
             CookieUtil.clearCookie(response);
             return  login(username,password);
         }else if (flag==1){
           ApiResult apiResult =  login(username,password);
           //登录成功的情况下；
           if (apiResult.getMsgCode()== ResultMsg.SUCCESS.getMsgCode()){
               try {
                   CookieUtil.saveCookie(username,password,response);
               } catch (Exception e) {
                   e.printStackTrace();
                   return  new ApiResult(ResultMsg.COOKIE_SAVE_FAILED,null);
               }
           }
           return  apiResult ;
         }
         return new ApiResult(LOGIN_FLAG_IS_ILLEAGAL,null);
    }
    @Override
    public ApiResult getUserNameAndPass(HttpServletResponse response) {
        try {
        return   CookieUtil.readCooikeForLogin(request,response);
        } catch (IOException e) {
            e.printStackTrace();
            return  new ApiResult(COOKIE_IS_NULL,null) ;
        }

    }
    @Override
    public User getCurrentUser(HttpServletRequest request){
        String auth = request.getHeader("token");
        if(StringUtils.isEmpty(auth)){
            auth = request.getParameter("token");
        }
        Claims claims =null;
        try{
            claims = JwtUtil.parseJWT(auth);
        }catch(Exception e){
            return  null;
        }
        String id = claims.getId();
        if (StringUtils.isEmpty(id)) {
            return  null;
        }
        User user = userDao.getById(id);
        if(user==null){
            return  null;
        }
        return  user;
    }

    @Override
    public ApiResult getCurrentMember() {
        return new ApiResult(NOT_FOUND,null);
//        Member member = (Member) request.getSession().getAttribute("user");
//        if (member==null){
//            return  new ApiResult(NOT_FOUND,null);
//        }else{
//            return  new ApiResult(SUCCESS,member);
//        }
    }

    @Override
    public ApiResult changeUserPass(Long id, String loginName, String pass ,String passOrigin) {

        ApiResult userResult = userService.findByIdWithApiResult(id);
        if (userResult.isFailed()){
            return  userResult ;
        }
        User user = (User) userResult.getData();

        if (StringUtils.isEmpty(passOrigin)){
            return  new ApiResult("原登录密码不能够为空");
        }
        if (!encoder.passwordEncoderByMd5(passOrigin).equals(user.getPassWord())){
            return  new ApiResult("原登录密码不正确");
        }
        if (StringUtils.isEmpty(loginName)){
            return new ApiResult(LOGIN_NAME_IS_NULL,null);
        }
        if (StringUtils.isEmpty(pass)){
            return  new ApiResult(PASSWORD_IS_NULL,null);
        }
        if (!loginName.equals(user.getLoginName())){
        User userForCheck  =userService.findUserByLoginName(loginName);
        if (userForCheck!=null){
            return  new ApiResult(LOGIN_NAME_REPEATED,null) ;
        }
        }
        user.setLoginName(loginName);
        user.setPassWord(encoder.passwordEncoderByMd5(pass));
        try {
         user=   userService.saveOrUpdate(user);

         setuserInfo(user);

        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return new ApiResult(e.getResultMsg(),null);
        }
        return new ApiResult(SUCCESS,user);
    }


    @Override
    public ApiResult resetPassword(String loginName,String loginPass){
        User user = userService.findUserByLoginName(loginName);
        if(user == null){
            return new ApiResult("登录账号不存在");
        }
        user.setPassWord(encoder.passwordEncoderByMd5(loginPass));
        try {
            userService.update(user);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }
        return new ApiResult(SUCCESS,user);
    }
}
