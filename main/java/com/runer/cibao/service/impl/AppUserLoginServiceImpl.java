package com.runer.cibao.service.impl;

import com.runer.cibao.Config;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.IRedisService;
import com.runer.cibao.dao.AppUserDao;
import com.runer.cibao.domain.*;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.*;
import com.runer.cibao.util.Encoder;
import com.runer.cibao.util.JwtUtil;
import com.runer.cibao.util.machine.IdMachine;
import com.runer.cibao.util.machine.VerifyCodeMachine;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.util.ListUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.runer.cibao.exception.ResultMsg.*;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/12
 **/

@Service
public class AppUserLoginServiceImpl extends IRedisService implements AppUserLoginService {

    @Autowired
    AppUserService appUserService;

    @Autowired
    Encoder encoder;


    @Autowired
    AppUserDao appUserDao;


    @Autowired
    CodeService codeService;


    @Autowired
    VerifyCodeMachine verifyCodeMachine;

    @Autowired
    PersonalLearnBookService personalLearnBookService;

    @Autowired
    SendSMSServiceImpl sendSMSService;

    @Autowired
    LearnTimeService learnTimeService;

    @Autowired
    SignTimeRecordService signTimeRecordService;

    @Value("${verify-code-length}")
    int code_length;
    @Autowired
    JwtUtil jwtUtil;

    @Override
    public ApiResult login(String phone, String pass) {
        Map<String, Object> map = new HashMap<>();
        ApiResult apiResult;

        apiResult = appUserService.findAppUserByPhoneNum(phone);

        //判断uid进行登录的情况下；
        if (apiResult.isFailed()) {
            apiResult = appUserService.findAppUserByUid(phone);
        }

        if (apiResult.isFailed()) {
            return new ApiResult(ResultMsg.USER_IS_NOT_EXIST, null);
        }
        if (StringUtils.isEmpty(phone)) {
            return new ApiResult(ResultMsg.PHONE_NUMBER_IS_NULL, null);
        }
        if (StringUtils.isEmpty(pass)) {
            return new ApiResult(ResultMsg.PASSWORD_IS_NULL, null);
        }
        if (pass.length() < 6) {
            return new ApiResult(PASSWORD_LENGTH_IS_6, null);
        }
        AppUser appUser = (AppUser) apiResult.getData();

        String eqPass = encoder.passwordEncoderByMd5(pass);

        if (eqPass.equals(appUser.getPassword())) {
            //记录登录时间
            SignTimeRecord signTimeRecord = new SignTimeRecord();
            signTimeRecord.setSignDate(new Date());
            signTimeRecord.setUserId(appUser.getId());
            signTimeRecordService.saveRecord(signTimeRecord);

            //设置当天学习的时间；
            List<LearnTime> learnTimes = (List<LearnTime>) learnTimeService.getUploadLearnTime(appUser.getId(), new Date()).getData();
            if (!ListUtils.isEmpty(learnTimes)) {
                appUser.setLearnTime(learnTimes.get(0).getTime());
            }

            ApiResult apiResult1 = personalLearnBookService.getCurrentBook(appUser.getId());

            PersonalLearnBook personalLearnBook = (PersonalLearnBook) apiResult1.getData();
            if (appUser.hasCompeltedInfos()) {
                appUser.setHasCompeltedInfo(true);
            }
            map.put("appUser", appUser);
            if (personalLearnBook == null) {
                map.put("personalLearnBookId", "");
                map.put("bookId", "");
            } else {
                map.put("personalLearnBookId", personalLearnBook.getId());
                map.put("bookId", personalLearnBook.getLearnBook().getId());
            }
            return new ApiResult(ResultMsg.SUCCESS, map);
        } else {
            return new ApiResult(ResultMsg.PASSWORID_IS_ILLEGEAL, null);
        }
    }

    @Override
    public ApiResult sendCode(String phone, Integer type) {

        if (StringUtils.isEmpty(phone)) {
            return new ApiResult(PHONE_NUMBER_IS_NULL, null);
        }
        if (type == null) {
            new ApiResult(CODE_TYPE_IS_NUll, null);
        }
        ApiResult phoneResult = appUserService.findAppUserByPhoneNum(phone);

        if (type == Config.REGISTER || type == Config.BIND_PHONE) {
            if (phoneResult.isSuccess()) {
                return new ApiResult("此用户已注册");
            }
        }
        if (type == Config.UPDATE_PASS_OR_FORGET) {
            if (phoneResult.isFailed()) {
                return new ApiResult("此用户未注册");
            }
        }
        ApiResult apiResult = codeService.getOnePhoneCode(phone, type);
        VerifyCode code2 = (VerifyCode) apiResult.getData();
        if (code2 != null) {
            long nowTime = System.currentTimeMillis() / 1000;
            if ((nowTime - code2.getTime()) < 60) {
                return new ApiResult("一分钟后才能再次发送");
            }
        }


        //codeRandom ;
        String code = encoder.generateRandomNumber(code_length);
        System.err.println("======" + code);
        if (!sendSMSService.sendCode(phone, code, type)) {
            return new ApiResult(CODE_SEND_FAILED, null);
        }
        // String code =  verifyCodeMachine.generateVerifyCodeJushNum(code_length);
        long time = System.currentTimeMillis() / 1000;
        codeService.saveOnePhoneCode(phone, code, type, time);
        return new ApiResult(ResultMsg.SUCCESS, code);
    }

    @Override
    public ApiResult register(String phone, String pass) {

        ApiResult apiResult = appUserService.findAppUserByPhoneNum(phone);

        if (apiResult.getMsgCode() == ResultMsg.SUCCESS.getMsgCode()) {
            return new ApiResult(ResultMsg.USER_IS_EXISTED, null);
        }
        if (StringUtils.isEmpty(phone)) {
            return new ApiResult(ResultMsg.PHONE_NUMBER_IS_NULL, null);
        }

        if (StringUtils.isEmpty(pass)) {
            return new ApiResult(ResultMsg.PASSWORD_IS_NULL, null);
        }
        if (pass.length() < 6) {
            return new ApiResult(PASSWORD_LENGTH_IS_6, null);
        }


        String dbPass = encoder.passwordEncoderByMd5(pass);

        AppUser appUser = new AppUser();

        appUser.setPhoneNum(phone);
        appUser.setPassword(dbPass);
        appUser.setName(phone);
        appUser.setLevel("LV0");
        appUser.setRegisterDate(new Date());
        String uid = createUid();
        appUser.setUid(uid);
        appUser.setRegisterDate(new Date());

        try {

            appUser = appUserService.save(appUser);

            appUser.setHasCompeltedInfo(appUser.hasCompeltedInfos());

            //记录登录时间 避免注册后不登录的用户 没有开始学习时间和上次在线时间
            SignTimeRecord signTimeRecord = new SignTimeRecord();
            signTimeRecord.setSignDate(new Date());
            signTimeRecord.setUserId(appUser.getId());
            signTimeRecordService.saveRecord(signTimeRecord);

            return new ApiResult(ResultMsg.SUCCESS, appUser);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return new ApiResult(e.getResultMsg(), null);
        }
    }

    private String createUid() {
        String uid18 = null;
        try {
            uid18 = String.valueOf(IdMachine.getFlowIdWorkerInstance().nextId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String uid = uid18.substring(7);
        AppUser appUser = appUserDao.findByUid(uid);
        if (appUser == null) {
            return uid;
        } else {
            return createUid();
        }

    }

    @Override
    public ApiResult forgetPass(String phone, String code, String pass) {
        ApiResult apiResult = appUserService.findAppUserByPhoneNum(phone);

        if (apiResult.getMsgCode() != ResultMsg.SUCCESS.getMsgCode()) {
            return new ApiResult(ResultMsg.USER_IS_NOT_EXIST, null);
        }
        if (StringUtils.isEmpty(phone)) {
            return new ApiResult(ResultMsg.PHONE_NUMBER_IS_NULL, null);
        }
        if (StringUtils.isEmpty(code)) {
            return new ApiResult(ResultMsg.CODE_IS_NULL, null);
        }
        if (StringUtils.isEmpty(pass)) {
            return new ApiResult(ResultMsg.PASSWORD_IS_NULL, null);
        }
        if (pass.length() < 6) {
            return new ApiResult(PASSWORD_LENGTH_IS_6, null);
        }

        ApiResult codeResult = verfiyCode(phone, Config.UPDATE_PASS_OR_FORGET, code);
        if (codeResult.isFailed()) {
            return codeResult;
        }
        String dbPass = encoder.passwordEncoderByMd5(pass);

        AppUser appUser = (AppUser) apiResult.getData();

        appUser.setPassword(dbPass);

        try {
            appUser = appUserService.save(appUser);
            return new ApiResult(ResultMsg.SUCCESS, appUser);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return new ApiResult(e.getResultMsg(), null);
        }
    }

    @Override
    public ApiResult thirdLogin(String openId, String thirdNum, Integer loginType, String headerImgUrl, String nickName) {
        /**
         * 第一次登录，新用户
         */
        Map<String, Object> map = new HashMap<>();
        AppUser appUser = null;
        try {
            appUser = appUserDao.findAppUserByOpenId(loginType, openId);
            //注册新的用户
            if (appUser == null) {
                appUser = new AppUser();
                appUser.setRegisterDate(new Date());
                appUser.setThirdImgUrl(headerImgUrl);
                appUser.setThirdNickName(nickName);
                appUser.setName(nickName);
                appUser.setImgUrl(headerImgUrl);
                switch (loginType) {
                    case Config.WX:
                        appUser.setWechatNum(thirdNum);
                        appUser.setWechatOpenId(openId);
                        appUser.setWechatNickName(nickName);
                        break;
                    case Config.QQ:
                        appUser.setQqNumber(thirdNum);
                        appUser.setQqOpenId(openId);
                        appUser.setQqNickName(nickName);
                        break;
                    case Config.WEIBO:
                        appUser.setWeiboNumber(thirdNum);
                        appUser.setWeiboOpenId(openId);
                        appUser.setWeiboNickName(nickName);
                        break;
                }
                String uid = createUid();
                appUser.setUid(uid);
                appUser.setRegisterDate(new Date());
                appUser = appUserService.save(appUser);
            }
            //直接进行登录！！
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return new ApiResult(e.getResultMsg(), null);
        }
        ApiResult apiResult1 = personalLearnBookService.getCurrentBook(appUser.getId());
        PersonalLearnBook personalLearnBook = (PersonalLearnBook) apiResult1.getData();

        if (appUser.hasCompeltedInfos()) {
            appUser.setHasCompeltedInfo(true);
        }

        //记录登录时间
        SignTimeRecord signTimeRecord = new SignTimeRecord();
        signTimeRecord.setSignDate(new Date());
        signTimeRecord.setUserId(appUser.getId());
        signTimeRecordService.saveRecord(signTimeRecord);

        //设置当天学习的时间；
        List<LearnTime> learnTimes = (List<LearnTime>) learnTimeService.getUploadLearnTime(appUser.getId(), new Date()).getData();
        if (!ListUtils.isEmpty(learnTimes)) {
            appUser.setLearnTime(learnTimes.get(0).getTime());
        }

        map.put("appUser", appUser);


        if (personalLearnBook == null) {
            map.put("personalLearnBookId", "");
            map.put("bookId", "");
        } else {
            map.put("personalLearnBookId", personalLearnBook.getId());
            map.put("bookId", personalLearnBook.getLearnBook().getId());
        }

        return new ApiResult(SUCCESS, map);
    }


    @Override
    public ApiResult bindThirdAccount(Long userId, String openId, Integer loginType, String nickName) {
        AppUser appUser = null;
        try {
            appUser = appUserService.findById(userId);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return new ApiResult(e.getResultMsg(), null);
        }
        /**
         * 查找是否存在，判断是否需要进行绑定
         */
        try {
            AppUser thirdUser = appUserDao.findAppUserByOpenId(loginType, openId);
            if (thirdUser != null) {
                return new ApiResult("该微信号已被使用");
            }
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return new ApiResult(e.getResultMsg(), null);
        }

        switch (loginType) {
            case Config.WX:
                appUser.setWechatOpenId(openId);
                appUser.setWechatNum(nickName);
                appUser.setWechatNickName(nickName);
                break;
            case Config.WEIBO:
                appUser.setWeiboOpenId(openId);
                appUser.setWeiboNumber(nickName);
                appUser.setWechatNickName(nickName);
                break;
            case Config.QQ:
                appUser.setQqOpenId(openId);
                appUser.setQqNumber(nickName);
                appUser.setQqNickName(nickName);
                break;
        }

        try {
            appUser = appUserService.saveOrUpdate(appUser);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return new ApiResult(e.getResultMsg(), null);
        }
        return new ApiResult(SUCCESS, appUser);
    }

    @Override
    public ApiResult bindPhone(Long userId, String phone, String codeNum, String pass) {
        /**
         * 判定是否已经绑定
         */
        ApiResult appUserResult = appUserService.findAppUserByPhoneNum(phone);
        if (appUserResult.getMsgCode() == SUCCESS.getMsgCode()) {
            return new ApiResult(USER_IS_EXISTED, null);
        }

        AppUser appUser = null;

        try {
            appUser = appUserService.findById(userId);

            /**
             * 验证验证码 ；
             */
            ApiResult codeResult = verfiyCode(phone, Config.BIND_PHONE, codeNum);
            if (codeResult.isFailed()) {
                return codeResult;
            }


            /**
             若是第一次使用手机号的情况下，需要绑定密码
             */
            if (StringUtils.isEmpty(appUser.getPassword())) {
                /**
                 * 密码不能为空，若是第一次绑定手机的话；
                 */
                if (StringUtils.isEmpty(pass)) {
                    return new ApiResult(PASSWORD_IS_NULL_FOR_BINDING_PHONE, null);
                }

                String passWord = encoder.passwordEncoderByMd5(pass);
                appUser.setPassword(passWord);
            }


            appUser.setPhoneNum(phone);

            appUser = appUserService.saveOrUpdate(appUser);

            return new ApiResult(SUCCESS, appUser);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return new ApiResult(e.getResultMsg(), null);
        }

    }

    @Override
    public ApiResult onlyVerifyCode(String phone, String code, Long type) {

        ApiResult apiResult = appUserService.findAppUserByPhoneNum(phone);

        if (type == Config.REGISTER || type == Config.BIND_PHONE) {
            if (apiResult.isSuccess()) {
                return new ApiResult(ResultMsg.PHONE_NUMBER_IS_REGISTED, null);
            }
        }

        if (type == Config.UPDATE_PASS_OR_FORGET) {
            if (apiResult.isFailed()) {
                return new ApiResult(ResultMsg.USER_IS_NOT_EXIST, null);
            }
        }
        if (StringUtils.isEmpty(phone)) {
            return new ApiResult(ResultMsg.PHONE_NUMBER_IS_NULL, null);
        }
        if (StringUtils.isEmpty(code)) {
            return new ApiResult(ResultMsg.CODE_IS_NULL, null);
        }
        ApiResult codeResult;
        codeResult = verfiyCode(phone, Integer.parseInt(type + ""), code);
        if (codeResult.isFailed()) {
            return codeResult;
        }
        return codeResult;
    }

    @Override
    public ApiResult setPassWord(String passWord) {
        String phone = (String) get("phone");
        ApiResult apiResult = appUserService.findAppUserByPhoneNum(phone);
        if (apiResult.getMsgCode() != ResultMsg.SUCCESS.getMsgCode()) {
            return new ApiResult(ResultMsg.USER_IS_NOT_EXIST, null);
        }
        if (StringUtils.isEmpty(phone)) {
            return new ApiResult(ResultMsg.PHONE_NUMBER_IS_NULL, null);
        }

        if (StringUtils.isEmpty(passWord)) {
            return new ApiResult(ResultMsg.PASSWORD_IS_NULL, null);
        }
        if (passWord.length() < 6) {
            return new ApiResult(PASSWORD_LENGTH_IS_6, null);
        }

        String dbPass = encoder.passwordEncoderByMd5(passWord);

        AppUser appUser = (AppUser) apiResult.getData();

        appUser.setPassword(dbPass);

        try {
            appUser = appUserService.save(appUser);
            return new ApiResult(ResultMsg.SUCCESS, appUser);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return new ApiResult(e.getResultMsg(), null);
        }
    }

    @Override
    public ApiResult unBindThirdNum(Long appUserId, int type) {

        ApiResult userResult = appUserService.findByIdWithApiResult(appUserId);

        AppUser user = null;
        if (userResult.isSuccess()) {
            user = (AppUser) userResult.getData();
        } else {
            return new ApiResult("用户不存在");
        }
        switch (type) {
            case Config.WX:
                String wxopenId = user.getWechatOpenId();
                if (StringUtils.isEmpty(wxopenId)) {
                    return new ApiResult("您还未绑定微信");
                }
                user.setWechatNickName(null);
                user.setWechatNum(null);
                user.setWechatOpenId(null);
                break;
            case Config.WEIBO:
                String sinaOpenId = user.getWeiboOpenId();
                if (StringUtils.isEmpty(sinaOpenId)) {
                    return new ApiResult("您还未绑定微博");
                }
                user.setWeiboNickName(null);
                user.setWeiboOpenId(null);
                user.setWeiboNumber(null);
                break;
            case Config.QQ:
                String qqOpenId = user.getQqOpenId();
                if (StringUtils.isEmpty(qqOpenId)) {
                    return new ApiResult("您还未绑定QQ");
                }
                user.setQqNickName(null);
                user.setQqNumber(null);
                user.setQqOpenId(null);
                break;
        }
        try {
            appUserService.save(user);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }
        return new ApiResult(SUCCESS, user);
    }

    @Override
    public ApiResult checkLoginUser(String userName, String pass) {
        if (StringUtils.isEmpty(userName)) {
            return new ApiResult(ResultMsg.USERNAME_IS_NULL, null);
        }
        if (StringUtils.isEmpty(pass)) {
            return new ApiResult(ResultMsg.PASSWORD_IS_NULL, null);
        }
        AppUser user = appUserDao.userExist(userName);
        if (user == null) {
            return new ApiResult(ResultMsg.USER_IS_NOT_EXIST, null);
        }
        String passExists = user.getPassword();
        String passNow = encoder.passwordEncoderByMd5(pass);
        if (!passNow.equals(passExists)) {
            return new ApiResult(ResultMsg.PASSWORID_IS_ILLEGEAL, null);
        }
        return new ApiResult(ResultMsg.SUCCESS, user);
    }

    @Override
    public AppUser getCurrentUser(HttpServletRequest request) {
        String auth = request.getHeader("token");
        if (StringUtils.isEmpty(auth)) {
            auth = request.getParameter("token");
        }
        Claims claims = null;
        try {
            claims = jwtUtil.parseJWT(auth);
        } catch (Exception e) {
            return null;
        }
        String id = claims.getId();
        System.out.println("token:id" + id);
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        AppUser user = null;
        try {
            user = appUserService.findById(Long.parseLong(id));
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return null;

        }

        return user;
    }


    private ApiResult verfiyCode(String phone, Integer type, String code) {
        ApiResult codeResult = codeService.getOnePhoneCode(phone, type);

        if (codeResult.getMsgCode() != SUCCESS.getMsgCode()) {
            return new ApiResult(CODE_IS_NOT_SEND_OR_TIMEOUT, null);
        }
        VerifyCode code1 = (VerifyCode) codeResult.getData();
        if (!code1.getCode().equals(code)) {
            return new ApiResult(CODE_IS_NOT_EQUAL, null);
        }

        return codeResult;
    }

    @Override
    protected String getRedisKey() {
        return null;
    }


}
