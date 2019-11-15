package com.runer.cibao.api;


import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.*;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.*;
import com.runer.cibao.util.JwtUtil;
import com.runer.cibao.util.machine.IdsMachine;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.ListUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author szhua
 * 2018-04-16
 * 14:46
 * @Descriptionsbaby_photos== UserApi
 **/

@RestController
@Api(value = "用户相关api", description = "用户相关")
@RequestMapping("api/appUser")
public class UserApi {

    @Autowired
    AppUserLoginService appUserLoginService;

    @Autowired
    AppUserService appUserService;

    @Autowired
    AppUserAccountService userAccountService;


    @Autowired
    AppUserBindSchoolService appUserBindSchoolService;

    @Autowired
    CodeService codeService;

    @Autowired
    OnlineTimeService onlineTimeService;

    @Autowired
    AdminConfigService adminConfigService;


    @Autowired
    LevelDesService levelDesService;

    @Autowired
    IntegralService integralService;

    @Autowired
    MedalsService medalsService;

    @Autowired
    PersonalLearnInfoService learnInfoService;


    @Autowired
    UserLoginService userLoginService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    RolesService rolesService;


    @ApiOperation(value = "getAllCodes", notes = "获得所有的验证码")
    @RequestMapping(value = "getAllCodes", method = RequestMethod.POST)
    public ApiResult getALlCodes(String key) {
        if (!"123321".equals(key)) {
            return null;
        }
        return codeService.getALlCode();
    }

    @ApiOperation(value = "login", notes = "登录")
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ApiResult login(String phone, String password) {
        ApiResult apiResult = appUserLoginService.checkLoginUser(phone, password);
        if (apiResult.isSuccess()) {
            AppUser user = (AppUser) apiResult.getData();
            String jwt = jwtUtil.createJWT(String.valueOf(user.getId()), phone);
            apiResult.setData(jwt);
            return apiResult;
        }
        return new ApiResult(ResultMsg.PASSWORID_IS_ILLEGEAL, null);
    }

    @ApiOperation(value = "注册", notes = "注册")
    @RequestMapping(value = "regist", method = RequestMethod.POST)
    public ApiResult regist(String phone, String pass) {
        return appUserLoginService.register(phone, pass);
    }

    @ApiOperation(value = "发送验证码", notes = "发送验证码")
    @RequestMapping(value = "sendCode", method = RequestMethod.POST)
    public ApiResult sendCode(String phone, Integer type) {
        return appUserLoginService.sendCode(phone, type);
    }

    @ApiOperation(value = "忘记密码", notes = "忘记密码")
    @RequestMapping(value = "forgePass", method = RequestMethod.POST)
    public ApiResult forgetPass(String phone, String code, String pass) {
        return appUserLoginService.forgetPass(phone, code, pass);
    }

    @ApiOperation(value = "完善用户的信息", notes = "完善用户信息")
    @RequestMapping(value = "updateUserInfo", method = RequestMethod.POST)
    public ApiResult updateUserInfo(Long id, String name, Long provinceId, Long cityId, Long areaId, Integer sex,
                                    String sign, String birthDay,
                                    Long classInSchoolId, MultipartFile headerFile) {
        //for birthDay 2018-12-12
        Date birth = null;
        try {
            if (!StringUtils.isEmpty(birthDay))
                birth = new SimpleDateFormat("yyyy-MM-dd").parse(birthDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return appUserService.updateAppUserInfo(id, name, provinceId, cityId, areaId, sex, sign, birth, classInSchoolId, headerFile);
    }

    @ApiOperation(value = "第三方登录", notes = "第三方登录  登录的类型 1:微信2:微博3：QQ")
    @RequestMapping(value = "thirdLogin", method = RequestMethod.POST)
    public ApiResult thirdLogin(String openId, String thirdNum, Integer loginType, String headerImgUrl, String nickName) {
        return appUserLoginService.thirdLogin(openId, thirdNum, loginType, headerImgUrl, nickName);
    }

    @ApiOperation(value = "绑定第三方账号", notes = "绑定第三方账号")
    @RequestMapping(value = "bindThirdNum", method = RequestMethod.POST)
    public ApiResult bindThirdNum(Long userId, String openId, Integer loginType, String thirdNum) {
        return appUserLoginService.bindThirdAccount(userId, openId, loginType, thirdNum);
    }

    @ApiOperation(value = "解绑第三方账号", notes = "解绑第三方账号")
    @RequestMapping(value = "unBindThirdNum", method = RequestMethod.POST)
    public ApiResult unBindThirdNum(Long userId, int type) {
        return appUserLoginService.unBindThirdNum(userId, type);
    }

    @ApiOperation(value = "绑定手机号", notes = "绑定手机号")
    @RequestMapping(value = "bindPhoneNum", method = RequestMethod.POST)
    public ApiResult bindPhoneNum(Long userId, String phone, String codeNum, String pass) {
        return appUserLoginService.bindPhone(userId, phone, codeNum, pass);
    }

    @ApiOperation(value = "获得个人详情", notes = "获得个人详情")
    @RequestMapping(value = "getUserDetail", method = RequestMethod.POST)
    public ApiResult getUserDetail(Long userId) {
        return appUserService.findUserAndIntegral(userId);
    }

    @ApiOperation(value = "获得个人账户信息", notes = "获得个人账户信息")
    @RequestMapping(value = "getUserAccountDetail", method = RequestMethod.POST)
    public ApiResult getAccountDetail(Long userId) {
        return userAccountService.generateByUserId(userId);
    }

    @ApiOperation(value = "绑定学校ID", notes = "绑定学校ID")
    @RequestMapping(value = "bindSchoolId", method = RequestMethod.POST)
    public ApiResult bindSchoolId(Long userId, String schoolId) {
        return appUserBindSchoolService.bindSchool(userId, schoolId);
    }

    @ApiOperation(value = "用户在线时长", notes = "用户在线时长")
    @RequestMapping(value = "getOnlineTime", method = RequestMethod.POST)
    public ApiResult onlineTime(Long userId) {
        return onlineTimeService.getOnlineTime(userId);
    }

    @ApiOperation(value = "用户的学习时长", notes = "用户在线时长")
    @RequestMapping(value = "getLearnTime", method = RequestMethod.POST)
    public ApiResult learnTime(Long userId) {
        return onlineTimeService.getOnlineTime(userId);
    }


    @ApiOperation(value = "上传用户头像", notes = "上传用户头像")
    @RequestMapping(value = "uploadUserImag", method = RequestMethod.POST)
    public ApiResult uploadUserImag(Long userId, MultipartFile img) {
        return appUserService.uploadUserImag(userId, img);
    }

    @ApiOperation(value = "校验验证码", notes = "校验验证码")
    @RequestMapping(value = "verifyCode", method = RequestMethod.POST)
    public ApiResult verifyCode(String phone, String code, Long type) {

        return appUserLoginService.onlyVerifyCode(phone, code, type);
    }

    @ApiOperation(value = "设置密码", notes = "设置密码")
    @RequestMapping(value = "setPass", method = RequestMethod.POST)
    public ApiResult setPass(String pass) {
        return appUserLoginService.setPassWord(pass);
    }


    @ApiOperation(value = "获得系统配置", notes = "获得系统配置")
    @RequestMapping(value = "getSystemConfig", method = RequestMethod.POST)
    public ApiResult getSystemConfig() {
        return adminConfigService.forceGetAdminConfig();
    }

    @ApiOperation(value = "获得积分等级说明", notes = "获得积分等级说明")
    @RequestMapping(value = "getLevelDes", method = RequestMethod.POST)
    public ApiResult getLevelDes() {
        List<Leveldes> levels = levelDesService.findAll();
        ;
        if (ListUtils.isEmpty(levels)) {
            Leveldes leveldes = new Leveldes();
            leveldes.setL1_10(1000);
            leveldes.setL11_20(5000);
            leveldes.setL21_30(10000);
            leveldes.setL31_40(20000);
            leveldes.setL41_50(50000);
            leveldes.setL51_up(50001);
            try {
                levelDesService.saveOrUpdate(leveldes);
            } catch (SmartCommunityException e) {
                e.printStackTrace();
            }
            return new ApiResult(ResultMsg.SUCCESS, leveldes);
        }
        return new ApiResult(ResultMsg.SUCCESS, levels.get(0));
    }

    @ApiOperation(value = "获得等级的详细情况", notes = "获得等级的详细情况")
    @RequestMapping(value = "getLevelDetail", method = RequestMethod.POST)
    public ApiResult getLevelDetail(Long appUserId) {
        ApiResult userResult = appUserService.findByIdWithApiResult(appUserId);
        if (userResult.isFailed()) {
            return new ApiResult("用户不存在");
        }
        AppUser appUser = (AppUser) userResult.getData();
        String img = appUser.getImgUrl();
        String levelName = appUser.getLevel();
        Integral integralBean = (Integral) integralService.findUserIntegral(appUserId).getData();
        Long integral = integralBean.getTotal();
        int cuurentRange;
        if (integral > 1 && integral <= 1000) {
            cuurentRange = 1000;
        } else if (integral > 1000 && integral <= 5000) {
            cuurentRange = 5000;
        } else if (integral > 5000 && integral <= 10000) {
            cuurentRange = 1000;
        } else if (integral > 10000 && integral <= 20000) {
            cuurentRange = 20000;
        } else if (integral > 20000 && integral <= 50000) {
            cuurentRange = 50000;
        } else if (integral > 50000) {
            cuurentRange = 50000;
        } else {
            cuurentRange = 1000;
        }
        Map<String, Object> result = new HashMap<>();
        result.put("img", img);
        result.put("levelName", levelName);
        result.put("integral", integral);
        result.put("cuurentRange", cuurentRange);
        return new ApiResult(ResultMsg.SUCCESS, result);

    }

    @ApiOperation(value = "获得个人的奖牌", notes = "获得个人的奖牌")
    @RequestMapping(value = "getPersonalMeals", method = RequestMethod.POST)
    public ApiResult getPersonalMeals(Long appUserId) {
        return medalsService.findAppusersMedals(appUserId);
    }


    @ApiOperation(value = "强制的获取奖牌", notes = "强制的获取奖牌")
    @RequestMapping(value = "getPersonalMealsA", method = RequestMethod.POST)
    public ApiResult getPersonalMealsA() {

        return learnInfoService.ditrubePersonsHornor();
    }


    @ApiOperation(value = "获取当前登录用户的信息", notes = "获取当前登录用户的信息")
    @RequestMapping(value = "getCurrentUser", method = RequestMethod.GET)
    public ApiResult getCurrentUser(HttpServletRequest request) {
        User user = userLoginService.getCurrentUser(request);
        if (user == null) {
            return new ApiResult(ResultMsg.ENTITY_ID_NOT_EXISTS, null);
        }
        String rolesIds = user.getRolesIds();
        if (!org.springframework.util.StringUtils.isEmpty(rolesIds)) {
            try {
                String ids = new IdsMachine().deparseIdsToNormal(rolesIds);
                List<Roles> roles = rolesService.findByIds(ids);
                user.setRoles(roles);
            } catch (SmartCommunityException e) {
                return new ApiResult(ResultMsg.ENTITY_ID_NOT_EXISTS, null);
            }
        }
        return new ApiResult(ResultMsg.SUCCESS, user);
    }

    @ApiOperation(value = "获取当前登录用户的信息", notes = "获取当前登录用户的信息")
    @RequestMapping(value = "getCurrentAppUser", method = RequestMethod.GET)
    public ApiResult getCurrentAppUser(HttpServletRequest request) {
        AppUser user = appUserLoginService.getCurrentUser(request);
        if (user == null) {
            return new ApiResult(ResultMsg.ENTITY_ID_NOT_EXISTS, null);
        }
        return new ApiResult(ResultMsg.SUCCESS, user);
    }


}
