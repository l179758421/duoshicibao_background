package com.runer.cibao.service.impl;

import com.runer.cibao.Config;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.AppUserDao;
import com.runer.cibao.dao.UidUserDao;
import com.runer.cibao.domain.AppUser;
import com.runer.cibao.domain.School;
import com.runer.cibao.domain.UidUser;
import com.runer.cibao.domain.UidUserExcel;
import com.runer.cibao.domain.repository.UidUserRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.SchoolServivce;
import com.runer.cibao.service.UidUserService;
import com.runer.cibao.util.Encoder;
import com.runer.cibao.util.ExcelUtil;
import com.runer.cibao.util.machine.IdMachine;
import com.runer.cibao.util.page.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.runer.cibao.exception.ResultMsg.SUCCESS;

@Service
public class UidUserServiceImpl extends BaseServiceImp<UidUser, UidUserRepository> implements UidUserService {

    @Autowired
    UidUserDao uidUserDao;
    @Autowired
    AppUserDao appUserDao;

    @Autowired
    Encoder encoder;
    @Autowired
    SchoolServivce schoolServivce;

    @Override
    public Page<UidUser> findUidUser(String Uid, Integer page, Integer limit) {

        Page<UidUser> pages = uidUserDao.findUidUser(Uid, PageableUtil.basicPage(page, limit));

        for (UidUser uidUser : pages.getContent()) {
               //解密密码
                uidUser.setPassWord(decodePwd(uidUser.getPassWord()));
        }

        return pages;
    }

    @Override
    public ApiResult batchCreateUID(Integer num, String pwd, Long schoolId) {

        ApiResult schoolResult = schoolServivce.findByIdWithApiResult(schoolId);
        if (schoolResult.isFailed()) {
            new ApiResult("使用学校不存在");
        }
        School school = (School) schoolResult.getData();
        String str = Config.PASS_SALT + pwd;
        BASE64Encoder encoder = new BASE64Encoder();
        String encoderPwd = encoder.encode(str.getBytes());
        List<UidUser> uidUsers = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            UidUser uidUser = new UidUser();
            uidUser.setCreateTime(new Date());
            uidUser.setSchool(school);
            String uid=createUid();
            uidUser.setUid(uid);
            uidUser.setPassWord(encoderPwd);
            uidUsers.add(uidUser);
        }
        uidUsers = r.saveAll(uidUsers);
        return new ApiResult(SUCCESS, uidUsers);
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
    public ApiResult exportUid(HttpServletResponse response) {
        List<UidUser> uidUserList = r.findAll();

        if (ListUtils.isEmpty(uidUserList)) {
            return new ApiResult("数据为空");
        }

        List<UidUserExcel> uidUserExcels = new ArrayList<>();
        for (UidUser uidUser : uidUserList) {
            UidUserExcel excel = new UidUserExcel();
            excel.setUID(uidUser.getUid());
            excel.setSchool(uidUser.getSchool().getName());
            excel.setCreateTime(uidUser.getCreateTime());
            excel.setPassWord(decodePwd(uidUser.getPassWord()));
            uidUserExcels.add(excel);
        }
        ExcelUtil.exportExcel(uidUserExcels, "uid文档", "uid", UidUserExcel.class, "uid.xls", response);
        return new ApiResult(ResultMsg.SUCCESS, null);
    }

    /**
     * 密码解密
     * @param pwd
     * @return
     */
    private static String decodePwd(String pwd) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            String saltPwd = new String(decoder.decodeBuffer(pwd));
            String passWord = saltPwd.substring(Config.PASS_SALT.length());
            return passWord;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }
}
