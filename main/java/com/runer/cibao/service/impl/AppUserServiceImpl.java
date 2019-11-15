package com.runer.cibao.service.impl;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.runer.cibao.Config;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.AppUserDao;
import com.runer.cibao.domain.*;
import com.runer.cibao.domain.repository.AppUserRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.*;
import com.runer.cibao.util.Arith;
import com.runer.cibao.util.Encoder;
import com.runer.cibao.util.ExcelUtil;
import com.runer.cibao.util.NormalUtil;
import com.runer.cibao.util.machine.DateMachine;
import com.runer.cibao.util.machine.IdMachine;
import com.runer.cibao.util.machine.IdsMachine;
import com.runer.cibao.util.page.PageableUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.ListUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;

import static com.runer.cibao.exception.ResultMsg.SUCCESS;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/12
 **/
@Service
public class AppUserServiceImpl extends BaseServiceImp<AppUser, AppUserRepository> implements AppUserService {


    @Autowired
    AppUserDao appUserDao;
    @Autowired
    ClassInSchoolService classInSchoolService;
    @Autowired
    AreaService areaService;
    @Autowired
    IntegralService integralService;
    @Autowired
    SchoolServivce schoolServivce;
    @Autowired
    Encoder myEncoder;
    @Autowired
    SignTimeRecordService signTimeRecordService;
    @Autowired
    OnlineTimeService onlineTimeService;
    @Autowired
    PersonalLearnInfoService personalLearnInfoService;
    @Autowired
    AppUserService appUserService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    PunshCardService punshCardService;
    @Autowired
    LearnTimeService learnTimeService;
    @Autowired
    AppUserAccountService AppUserAccountService;


    @Value("${web.upload-path}")
    private String upload_path;

    @Value("${web.upload-basePath}")
    private String upload_base_path;


    @Override
    public Page<AppUser> findAppUsers(String schoolUid, Long schoolId, Long classInSchoolId, String userName, Long teacherId, String uid, Integer isBatchCreated, Integer page, Integer limit) {
        Page<AppUser> datas = appUserDao.findAppUsers(schoolUid, schoolId, classInSchoolId, userName, teacherId, uid, isBatchCreated, PageableUtil.basicPage(page, limit));
        datas.forEach(appUser -> {
            int count = 0;
            PersonalLearnBooks books = appUser.getPersonalLearnBooks();
            if (books != null) {
                count = books.getPersonalLearnBooks() == null ? 0 : books.getPersonalLearnBooks().size();
            }
            appUser.setBooksCount(count);
        });
        return datas;
    }


    @Override
    public Page<AppUser> findAppUsersC(String schoolUid, Long schoolId, Long classInSchoolId, String userName, Long teacherId, String uid, Integer isBatchCreated, Integer page, Integer limit) {
        Page<AppUser> datas = appUserDao.findAppUsers2(schoolUid, schoolId, classInSchoolId, userName, teacherId, uid, isBatchCreated, PageableUtil.basicPage(page, limit));
        datas.forEach(appUser -> {
            int count = 0;
            PersonalLearnBooks books = appUser.getPersonalLearnBooks();
            if (books != null) {
                count = books.getPersonalLearnBooks() == null ? 0 : books.getPersonalLearnBooks().size();
            }
            appUser.setBooksCount(count);
        });
        return datas;
    }



    @Override
    public ApiResult findAppUserByPhoneNum(String phoneNum) {
        AppUser appUser = r.findAppUserByPhoneNum(phoneNum);
        if (appUser == null) {
            return new ApiResult(ResultMsg.USER_IS_NOT_EXIST, null);
        }
        return new ApiResult(ResultMsg.SUCCESS, appUser);
    }

    @Value("${web.upload-appPath}")
    private String appPath;

    @Value("${web.upload-app}")
    private String absolutePath;


    @Override
    public ApiResult updateAppUserInfo(Long id, String name, Long provinceId, Long cityId, Long areaId, Integer sex,
                                       String sign, Date birthDay,
                                       Long classInSchoolId, MultipartFile headerFile) {
        AppUser appUser = null;
        try {
            appUser = findById(id);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return new ApiResult(e.getResultMsg(), null);
        }
        //当是更新的情况；判断用户名是否重复
        if (id != null) {
            if (!StringUtils.isEmpty(name) && !name.equals(appUser.getName())) {
                if (r.findAppuserByName(name) != null) {
                    return new ApiResult(ResultMsg.USERNAME_IS_EXISTED, null);
                }
                appUser.setName(name);
            }
        }

        if (provinceId != null) {
            appUser.setProvinceId(provinceId);
        }
        if (areaId != null) {
            appUser.setAreaId(areaId);
        }
        if (cityId != null) {
            appUser.setCityId(cityId);
        }

        if (sex != null) {
            appUser.setSex(sex);
        }
        if (sex == null && appUser.getSex() == null) {
            appUser.setSex(Config.FEMALE);
        }

        if (!StringUtils.isEmpty(sign)) {
            appUser.setSign(sign);
        }

        if (birthDay != null) {
            appUser.setBirthDay(birthDay);
        }
        String addreeDetail = "";

        if (provinceId != null) {
            Province province = areaService.getProvince(provinceId);
            if (province != null) {
                addreeDetail += province.getName() + ",";
            }
        }

        if (cityId != null) {
            City city = areaService.findCityById(cityId);
            if (city != null) {
                addreeDetail += city.getName() + ",";
            }
        }

        if (areaId != null) {
            Area area = areaService.findAreaById(areaId);
            if (area != null) {
                addreeDetail += area.getName() + ",";
            }
        }

        appUser.setAddress(addreeDetail);

        if (classInSchoolId != null) {
            ClassInSchool classInSchool = new ClassInSchool();
            classInSchool.setId(classInSchoolId);
            appUser.setClassInSchool(classInSchool);
        }

        if (headerFile != null && !headerFile.isEmpty()) {
            ApiResult fileApiResult = NormalUtil.saveMultiFile(headerFile, appPath, absolutePath);
            if (fileApiResult.getMsgCode() == ResultMsg.SUCCESS.getMsgCode()) {
                appUser.setImgUrl((String) fileApiResult.getData());
            }
        }

        appUser = r.save(appUser);
        appUser = r.findById(appUser.getId()).get();
        return new ApiResult(ResultMsg.SUCCESS, appUser);

    }

    @Override
    public ApiResult findAppUserByUid(String uid) {
        AppUser appUser = r.findAppUserByUid(uid);
        if (appUser != null) {
            return new ApiResult(ResultMsg.SUCCESS, appUser);
        } else {
            return new ApiResult(ResultMsg.NOT_FOUND, null);
        }
    }

    @Override
    public ApiResult distributeClassInschool(Long userId, Long classInSchoolId) {
        ApiResult userResult = findByIdWithApiResult(userId);
        if (userResult.getMsgCode() != ResultMsg.SUCCESS.getMsgCode()) {
            return userResult;
        }
        AppUser appUser = (AppUser) userResult.getData();

        ApiResult classInSchoolResult = classInSchoolService.findByIdWithApiResult(classInSchoolId);
        if (classInSchoolResult.getMsgCode() != ResultMsg.SUCCESS.getMsgCode()) {
            return classInSchoolResult;
        }
        ClassInSchool classInSchool = (ClassInSchool) classInSchoolResult.getData();

        appUser.setClassInSchool(classInSchool);
        appUser.setClassInSchoolId(classInSchoolId);

        appUser = r.saveAndFlush(appUser);

        return new ApiResult(ResultMsg.SUCCESS, appUser);
    }

    @Override
    public ApiResult addUserToSChool(String userUid, Long schoolId) {
        ApiResult userReuslt = findAppUserByUid(userUid);
        if (userReuslt.getMsgCode() != ResultMsg.SUCCESS.getMsgCode()) {
            return userReuslt;
        }
        AppUser appUser = (AppUser) userReuslt.getData();

        ApiResult schoolResult = schoolServivce.findByIdWithApiResult(schoolId);
        if (schoolResult.getMsgCode() != ResultMsg.SUCCESS.getMsgCode()) {
            return schoolResult;
        }
        School school = (School) schoolResult.getData();

        if (school.getUid().equals(appUser.getSchoolId())){
            return  new ApiResult("此学员已存在");
        }

        appUser.setSchoolId(school.getUid());

        appUser = r.saveAndFlush(appUser);

        return new ApiResult(ResultMsg.SUCCESS, appUser);
    }

    @Override
    public ApiResult uniteSchools(Long id) {

        ApiResult userResult = findByIdWithApiResult(id);
        AppUser appUser = (AppUser) userResult.getData();

        appUser.setSchoolId(null);
        appUser.setClassInSchool(null);

        appUser = r.saveAndFlush(appUser);

        return new ApiResult(ResultMsg.SUCCESS, appUser);
    }

    @Override
    public ApiResult uniteSchoolsWithIds(String ids) {

        try {
            List<AppUser> datas = findByIds(ids);

            if (!ListUtils.isEmpty(datas)) {
                datas.forEach(appUser -> {
                    appUser.setClassInSchool(null);
                    appUser.setSchoolId(null);
                });
            }

            r.saveAll(datas);


        } catch (SmartCommunityException e) {
            e.printStackTrace();

            return new ApiResult(e.getResultMsg(), null);
        }
        return new ApiResult(ResultMsg.SUCCESS, null);
    }

    @Override
    public ApiResult perfectUserInfo(Long id, String phoneNum, String name) {

        ApiResult userResult = findByIdWithApiResult(id);
        if (userResult.getMsgCode() != ResultMsg.SUCCESS.getMsgCode()) {
            return userResult;
        }
        AppUser appUser = (AppUser) userResult.getData();
        appUser.setPhoneForInfo(phoneNum);
        appUser.setRealNameForInfo(name);
        appUser = r.saveAndFlush(appUser);
        return new ApiResult(ResultMsg.SUCCESS, appUser);
    }

    @Override
    public ApiResult findUserAndIntegral(Long appUserId) {
        Map<String, Object> map = new HashMap<>();
        ApiResult userResult = findByIdWithApiResult(appUserId);
        if (userResult.isFailed()) {
            return new ApiResult("用户不存在");
        }
        AppUser appUser = (AppUser) userResult.getData();

        if (!StringUtils.isEmpty(appUser.getSchoolId())){
            if (StringUtils.isEmpty(appUser.getSchoolName())){
              ApiResult schollResult =    schoolServivce.findSchoolByUID(appUser.getSchoolId()) ;
              if (!schollResult.isFailed()){
              School school = (School) schollResult.getData();
              appUser.setSchoolName(school.getName());
              r.saveAndFlush(appUser) ;
             }
            }
        }

        //防止以前的数据；
        if (appUser.getWordNum()==0){
            //第一次进行初始化；
            long  totalPersonlWordNum =0;
            List<PersonlLearnInfoBean> personlLearnInfoBeans  = (List<PersonlLearnInfoBean>) personalLearnInfoService.findOneLearn(appUserId).getData();
            if (!ListUtils.isEmpty(personlLearnInfoBeans)){
                for (PersonlLearnInfoBean personlLearnInfoBeanItem : personlLearnInfoBeans) {
                    totalPersonlWordNum+=personlLearnInfoBeanItem.getWordsNumNew() ;
                    System.err.println(personlLearnInfoBeanItem.getWordsNumNew());
                }
                appUser.setWordNum(totalPersonlWordNum);
            }
            try {
                appUserService.saveOrUpdate(appUser) ;
            } catch (SmartCommunityException e) {
                e.printStackTrace();
            }

        }

        if (appUser.getPunchCards()!=null){
           appUser.setPunchCardsDays(appUser.getPunchCards().size());
        }
        if (appUser.hasCompeltedInfos()){
            appUser.setHasCompeltedInfo(true);
        }




        map.put("appUser", appUser);
        ApiResult integralResult = integralService.findUserIntegral(appUserId);
        Integral integral = (Integral) integralResult.getData();
        map.put("totalIntegral", integral.getTotal());
        return new ApiResult(ResultMsg.SUCCESS, map);
    }


    @Override
    public ApiResult batchCreateUID(Integer num, String pwd, Long schoolId) {
        ApiResult schoolResult = schoolServivce.findByIdWithApiResult(schoolId);
        if (schoolResult.isFailed()) {
            new ApiResult("使用学校不存在");
        }
        School school = (School) schoolResult.getData();

        List<AppUser> uidUsers = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            AppUser uidUser = new AppUser();
            uidUser.setRegisterDate(new Date());
            uidUser.setSchoolId(String.valueOf(school.getUid()));
            //生成11位uid
            String uid = createUid();
            uidUser.setUid(uid);
            uidUser.setName(uidUser.getUid());
            uidUser.setIsBatchCreate(Config.isBatchCreate);
            uidUser.setSex(Config.FEMALE);
            uidUser.setPass64(myEncoder.encoder64(pwd));
            uidUser.setSchoolName(school.getName());
            uidUser.setPassword(myEncoder.passwordEncoderByMd5(pwd));
            uidUsers.add(uidUser);

        }
        uidUsers = r.saveAll(uidUsers);
        return new ApiResult(SUCCESS, uidUsers);
    }

    /**
     * 生成11位uid
     *
     * @return
     */
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
        List<AppUser> uidUserList = findAppUsers(null, null, null, null, null, null, Config.isBatchCreate, 1, Integer.MAX_VALUE).getContent();
        if (ListUtils.isEmpty(uidUserList)) {
            return new ApiResult("数据为空");
        }
        List<UidUserExcel> uidUserExcels = new ArrayList<>();
        for (AppUser uidUser : uidUserList) {
            UidUserExcel excel = userToExcel(uidUser);
            uidUserExcels.add(excel);
        }
        ExcelUtil.exportExcel(uidUserExcels, "uid文档", "uid", UidUserExcel.class, "uid.xls", response);
        return new ApiResult(ResultMsg.SUCCESS, null);
    }

    @Override
    public ApiResult exportUidByIds(String ids,String title,String sheetname) {
        List<AppUser> datas = appUserDao.findUserByIds(ids, Config.isBatchCreate);
        if (ListUtils.isEmpty(datas)) {
            return new ApiResult("数据为空");
        }
        List<UidUserExcel> uidUserExcels = new ArrayList<>();
        for (AppUser data : datas) {
            UidUserExcel excel = userToExcel(data);
            uidUserExcels.add(excel);
        }
        String  path=upload_path+sheetname;
        String  basePath=upload_base_path+sheetname;
        try {
            OutputStream out = new FileOutputStream(path);
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            Sheet sheet1 = new Sheet(1, 0, RedeemCodeExcel.class);
            sheet1.setSheetName(title);
            writer.write(uidUserExcels, sheet1);
            writer.finish();
        } catch (Exception e) {
            e.printStackTrace();
            return  new ApiResult(ResultMsg.OS_ERROR,null ) ;
        }
        return  new ApiResult(SUCCESS,basePath) ;
    }

    @Override
    public ApiResult findAppUserBySchoolId(String schoolUid) {
        List<AppUser> appUserList = r.findAppUserBySchoolId(schoolUid);
        return new ApiResult(SUCCESS,appUserList);
    }

    @Override
    public List<AppUser> findAppUserBySchoolId2(String schoolUid) {
        List<AppUser> appUserList = new ArrayList<AppUser>();
        if(!StringUtils.isEmpty(schoolUid)){
            appUserList = r.findAppUserBySchoolId(schoolUid);
        }

        return appUserList;
    }

    @Override
    public List<AppUser> findAppUserByClassInSchool(Long classId) {
        List<AppUser> appUserList = new ArrayList<AppUser>();
        if(classId != null){
            appUserList = r.findAppUserByClassInSchoolId(classId);
        }

        return appUserList;
    }

    @Override
    public Page<AppUser> findBySchoolUid(String schoolUid,String userName,Integer page, Integer limit) {
        return appUserDao.findBySchoolUid(schoolUid,userName,PageableUtil.basicPage(page, limit));
    }

    @Override
    public Page<AppUser> findByTeacherId(Long teacherId, String userName, Integer page, Integer limit) {
        return appUserDao.findAppUsers(null,null,null,userName,teacherId,null,null,PageableUtil.basicPage(page,limit));
    }

    @Override
    public AppUserDetail findAppUserDetail(Long userId) {
        AppUserDetail appUserDetail=new AppUserDetail();

        AppUser appUser = (AppUser) findByIdWithApiResult(userId).getData();
        appUserDetail.setName(appUser.getName());
        if(appUser.getClassInSchool() !=null){
            appUserDetail.setUserClass(appUser.getClassInSchool().getName());
        }else{
            appUserDetail.setUserClass("未分配班级");
        }

        List<SignTimeRecord> list=signTimeRecordService.findByUserId(userId);
        if(list.size()==0){
            appUserDetail.setNewSignTime("还未登录过");
        }else{
            appUserDetail.setNewSignTime(list.get(0).getSignDate().toString());
        }
        Long time=0l;
        List<OnlineTime>  onlineTimeList=onlineTimeService.findByUserId(userId);
        for (OnlineTime onlineTime : onlineTimeList) {
            time+=Long.valueOf(onlineTime.getTime());
        }
        Long onlineTime = time/60;
        //累计在线时长
        appUserDetail.setTotalOnlineTime(onlineTime.toString());
        return appUserDetail;
    }

    @Override
    public ApiResult exportReportExcel(Long classId, String fileName, HttpServletResponse response) {
        Page<AppUser> appUserPage = appUserService.findAppUsers(null, null, classId, null, null, null, null, null, null);
        for (AppUser appUser : appUserPage.getContent()) {
            Long index = 0L;
            Long learnTime =0L;
            List<LearnTime> learnTimes = learnTimeService.getLearnTime(appUser.getId());
            for (LearnTime lt:learnTimes) {
                if(lt.getTime() >= 1200000){
                    index+=1;
                }
                learnTime+=lt.getTime();
            }
            appUser.setName(appUser.getRealNameForInfo()==null?"":appUser.getRealNameForInfo());
            appUser.setStandardDay(index);
            appUser.setTodayStudyTime(Arith.dayHousMinS(learnTime/1000));
            appUser.setTotalNewVolidTime(Arith.dayHousMinS(learnTimes.get(0).getTime()/1000));
            List<SignTimeRecord> signTimeRecords = signTimeRecordService.findByUserId(appUser.getId());
            if (signTimeRecords.size() > 0) {
                //最近登录时间
                appUser.setLastSignTime(signTimeRecords.get(0).getSignDate().toString());
            }
            List<OnlineTime> onlineTimeList = onlineTimeService.getByUserId(appUser.getId());
            long totalTime = 0l;
            for (OnlineTime onlineTime : onlineTimeList) {
                totalTime += onlineTime.getTime();
            }
            appUser.setTotalStudyTime(Arith.dayHousMinS(totalTime));
            if(onlineTimeList.size() != 0){
                appUser.setTotalNewOnlineTime(Arith.dayHousMinS(onlineTimeList.get(0).getTime()));
            }else{
                appUser.setTotalNewOnlineTime(Arith.dayHousMinS(0l));
            }

        }

        List<StudyReportExcel> exportData=reportToExcels(appUserPage.getContent());
        System.err.println(JSON.toJSONString(exportData));

        Workbook work = ExcelUtil.exportExcel(exportData, null,fileName,
                StudyReportExcel.class, "test.xls", response);
        return  new ApiResult(SUCCESS,work.getSheetName(0));
    }

    @Override
    public ApiResult personalReportExcel(Long userId, String fileName, HttpServletResponse response) {
        List<PersonalReportBean> list = new ArrayList<>();
        PersonalReportBean personalReportBean = new PersonalReportBean();
        try {
            AppUser appUser = appUserService.findById(userId);
            personalReportBean.setName(appUser.getName());
            personalReportBean.setClassName(appUser.getClassInSchool().getName());
            personalReportBean.setPhone(appUser.getPhoneNum());
            ApiResult apiResult = schoolServivce.findSchoolByUID(appUser.getSchoolId());
            School school = (School) apiResult.getData();
            Page<Teacher> teachers = teacherService.findTeachers(school.getId(), null, null, null, 1, Integer.MAX_VALUE);
            for (Teacher teacher : teachers.getContent()) {
                String classIds = teacher.getClassIds();
                List<Long> ids = new IdsMachine().deparseIds(classIds);
                for (Long id : ids) {
                    if (id.equals(appUser.getClassInSchool().getId())) {
                        //查找班主任
                        personalReportBean.setHeadMaster(teacher.getName());
                    }
                }
            }
            List<SignTimeRecord> signTimeRecords = signTimeRecordService.findByUserId(appUser.getId());
            if (signTimeRecords.size() > 0) {
                SignTimeRecord signTimeRecord = signTimeRecords.get(signTimeRecords.size() - 1);
                //开始学习时间
                personalReportBean.setBenginStartTime(signTimeRecord.getSignDate().toString());
                ApiResult apiResult1 = punshCardService.findCards(appUser.getId(), null, null);
                if (apiResult1 != null) {
                    List<PunchCard> punchCards = (List<PunchCard>) apiResult1.getData();
                    //打卡天数
                    personalReportBean.setCardDays(punchCards.size());
                }
            }

        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }
        list.add(personalReportBean);
        List<PersonalReportExcel> exportData=personalReportToExcels(list);
        Workbook work = ExcelUtil.exportExcel(exportData, null, fileName, PersonalReportExcel.class, "test.xls", response);
        return  new ApiResult(SUCCESS,work.getSheetName(0)) ;
    }

    @Override
    public ApiResult personalReportExcel2(Long userId, String fileName, HttpServletResponse response) {
       List<AppUser> appUsers =  new ArrayList<>();
        AppUser appUser = null;
        try {
            appUser = appUserService.findById(userId);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }
        List<SignTimeRecord> signTimeRecords = signTimeRecordService.findByUserId(userId);
            if (signTimeRecords.size() > 0) {
                //最近登录时间
                appUser.setLastSignTime(signTimeRecords.get(0).getSignDate().toString());
            }
            List<OnlineTime> onlineTimeList = onlineTimeService.findByUserId(userId);
            long totalTime = 0l;
            for (OnlineTime onlineTime : onlineTimeList) {
                totalTime += onlineTime.getTime();
            }
            appUser.setTotalStudyTime(Arith.dayHousMinS(totalTime));
            List<OnlineTime> onlineTimeList1 = onlineTimeService.findByUserAndDate(userId, new Date());
            if (onlineTimeList1.size() > 0) {
                appUser.setTodayStudyTime(Arith.dayHousMinS(onlineTimeList1.get(0).getTime()));
            }
            ApiResult apiResult = personalLearnInfoService.findOneLearn(userId);
            List<PersonlLearnInfoBean> personlLearnInfoBean = (List<PersonlLearnInfoBean>) apiResult.getData();

        appUsers.add(appUser);
        List<StudyReportExcel> exportData=reportToExcels(appUsers);
        Workbook work = ExcelUtil.exportExcel(exportData, null, fileName, StudyReportExcel.class, "test.xls", response);
        return  new ApiResult(SUCCESS,work.getSheetName(0)) ;
    }


    @Override
    public ApiResult uploadUserImag(Long id, MultipartFile img) {
        AppUser appUser = null;
        try {
            appUser = findById(id);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return new ApiResult(e.getResultMsg(), null);
        }
        if (img != null && !img.isEmpty()) {
            ApiResult fileApiResult = NormalUtil.saveMultiFile(img, appPath, absolutePath);
            if (fileApiResult.getMsgCode() == ResultMsg.SUCCESS.getMsgCode()) {
                appUser.setImgUrl((String) fileApiResult.getData());
            }
        }
        r.saveAndFlush(appUser);

        return new ApiResult(ResultMsg.SUCCESS,appUser);
    }

    @Override
    public List<StudyReportExcel> reportToExcels(List<AppUser> list) {
        List<StudyReportExcel> studyReportExcels =new ArrayList<>() ;
        list.forEach(report -> {
            studyReportExcels.add(reportToExcel(report));
        });
        return studyReportExcels;
    }

    @Override
    public List<PersonalReportExcel> personalReportToExcels(List<PersonalReportBean> list) {
        List<PersonalReportExcel> personalReportExcels =new ArrayList<>() ;
        list.forEach(report -> {
            personalReportExcels.add(personalReportToExcel(report));
        });

        return personalReportExcels;
    }

    @Override
    public StudyReportExcel reportToExcel(AppUser appUser) {
        StudyReportExcel studyReportExcel= new StudyReportExcel();
        if(StringUtils.isEmpty(appUser.getSchoolName())){
            studyReportExcel.setUserSchool("");
        }else{
            studyReportExcel.setUserSchool(appUser.getSchoolName());
        }
        studyReportExcel.setUserClass(appUser.getClassInSchool().getName());
        studyReportExcel.setName(appUser.getName());
        studyReportExcel.setNewSignTime(appUser.getLastSignTime());
        studyReportExcel.setTotalOnlineTime(appUser.getTotalStudyTime());
        studyReportExcel.setTodayVolidTime(appUser.getTodayStudyTime());
        studyReportExcel.setTotalDays(appUser.getStandardDay());
        studyReportExcel.setTotalNewOnlineTime(appUser.getTotalNewOnlineTime());
        studyReportExcel.setTotalNewVolidTime(appUser.getTotalNewVolidTime());
        if(appUser.getPersonalLearnBooks() != null){
            List<PersonalLearnBook> personalLearnBooks = appUser.getPersonalLearnBooks().getPersonalLearnBooks();
            for (PersonalLearnBook pl:personalLearnBooks) {
                if(pl.getIsCurrentBook() == 1 && pl.getLearnBook()!=null){
                    studyReportExcel.setLearnBook(pl.getLearnBook().getBookName());
                    studyReportExcel.setLearnWords(String.valueOf(pl.getCurrentWordNum())+"/"+pl.getLearnBook().getWordsNum());
                }
            }
        }else{
            studyReportExcel.setLearnBook("");
            studyReportExcel.setLearnWords("");
        }

        studyReportExcel.setTotalWords(String.valueOf(appUser.getWordNum()));
        return studyReportExcel;
    }

    @Override
    public PersonalReportExcel personalReportToExcel(PersonalReportBean reportBean) {
        PersonalReportExcel personalReportExcel=new PersonalReportExcel();
        personalReportExcel.setName(reportBean.getName());
        personalReportExcel.setClassName(reportBean.getClassName());
        personalReportExcel.setHeadMaster(reportBean.getHeadMaster());
        personalReportExcel.setPhone(reportBean.getPhone());
        personalReportExcel.setBeginStudyTime(reportBean.getBenginStartTime());
        personalReportExcel.setPunchCardDays((int)reportBean.getCardDays());
        return personalReportExcel;
    }

    private UidUserExcel userToExcel(AppUser data) {
        UidUserExcel excel = new UidUserExcel();
        excel.setUID(data.getUid());
        excel.setSchool(data.getSchoolName());
        excel.setCreateTime(data.getRegisterDate());
        excel.setPassWord(myEncoder.decodePwd(data.getPass64()));
        return excel;
    }



    public PersonalReport personalReport(Long appUserId){
        PersonalReport personalReport = new PersonalReport();
        try {
            AppUser appUser = findById(appUserId);
            personalReport.setImgUrl(appUser.getImgUrl());//头像路径
            if(org.apache.http.util.TextUtils.isBlank(appUser.getRealNameForInfo())){
                personalReport.setUserName(appUser.getName());//学生姓名
            }else {
                personalReport.setUserName(appUser.getRealNameForInfo());//学生姓名
            }
            //性别
            Integer sex = appUser.getSex();
            if(sex == 0){
                personalReport.setSex("女");
            }else if(sex == 1){
                personalReport.setSex("男");
            }
            String schoolUid = appUser.getSchoolId();
            ApiResult apiResult = schoolServivce.findSchoolByUID(schoolUid);
            School school = (School) apiResult.getData();
            personalReport.setSchoolName(school.getName());//所在学校
            personalReport.setUserClass(appUser.getClassInSchool().getName());//所在班级
            Page<Teacher> teachers = teacherService.findTeachers(school.getId(), null, null, null, 1, Integer.MAX_VALUE);
            for (Teacher teacher : teachers.getContent()) {
                String classIds = teacher.getClassIds();
                List<Long> ids = new IdsMachine().deparseIds(classIds);
                for (Long id : ids) {
                    if (id.equals(appUser.getClassInSchool().getId())) {
                        //查找班主任
                        personalReport.setClassTeacher(teacher.getName());//班主任
                    }
                }
            }
            personalReport.setPhoneNum(appUser.getPhoneNum());//手机号
            PersonalLearnBooks books = appUser.getPersonalLearnBooks();
            Integer count = 0 ;
            if (books != null) {
                count = books.getPersonalLearnBooks() == null ? 0 : books.getPersonalLearnBooks().size();
            }
            personalReport.setBooksCount(count);//激活课本数
            ApiResult apiResult1 = AppUserAccountService.findAccountByUserId(appUserId);
            AppUserAccount appUserAccount = (AppUserAccount) apiResult1.getData();
            personalReport.setGoldCoins(appUserAccount.getGoldCoins());//当前金币余额
            if(appUser.getPersonalLearnBooks() != null){
                List<PersonalLearnBook> personalLearnBooks = appUser.getPersonalLearnBooks().getPersonalLearnBooks();
                for (PersonalLearnBook pl:personalLearnBooks) {
                    if(pl.getIsCurrentBook() == 1 && pl.getLearnBook() != null){
                        personalReport.setLearnBookName(pl.getLearnBook().getBookName());//当前学习课本
                        personalReport.setLearnBookNameId(pl.getLearnBook().getId());//当前学习课本ID
                        personalReport.setLearnBookWords(String.valueOf(pl.getCurrentWordNum())+"/"+pl.getLearnBook().getWordsNum());//学习进度
                    }
                }
            }else{
                personalReport.setLearnBookName("");
                personalReport.setLearnBookWords("");
            }
            ApiResult apiResult2 = personalLearnInfoService.findOneLearn(appUserId);
            List<PersonlLearnInfoBean> personlLearnInfoBean = (List<PersonlLearnInfoBean>) apiResult2.getData();
            Long number = 0L;
            if (personlLearnInfoBean != null) {
                for (PersonlLearnInfoBean pl:personlLearnInfoBean) {
                    Long index2 = pl.getWordsNumNew();
                    number += index2;
                }
            }
            personalReport.setUserWordsNumber(String.valueOf(number));//累计学习单词数
            List<SignTimeRecord> signTimeRecords = signTimeRecordService.findByUserId(appUserId);
            if (signTimeRecords.size() > 0) {
                personalReport.setLastSignTime(signTimeRecords.get(0).getSignDate().toString()); //最近登录时间
                SignTimeRecord signTimeRecord = signTimeRecords.get(signTimeRecords.size() - 1);
                personalReport.setStartLearnTime(signTimeRecord.getSignDate().toString());//开始学习时间
            }
            personalReport.setTotalStudyTime(Arith.dayHousMinS(onlineTime(appUserId,null)));    //总在线时长
            personalReport.setNewStudyTime(Arith.dayHousMinS(onlineTime(appUserId,new Date())));  //今日在线时长
            personalReport.setTodayStudyTime(Arith.dayHousMinS((LearnTime(appUserId,null))/1000));   //总学习时长
            personalReport.setNewLearnTimeDate(Arith.dayHousMinS((LearnTimeNEW(appUserId,new Date()))/1000));  //今日学习时长
            Date endDate = new Date();
            Date startDate = new DateMachine().getDaysBefore(30);
            personalReport.setStandardDaysNumber(String.valueOf(StandarDaysNumber(startDate,endDate,appUserId)));//近三十天达标天数
            personalReport.setStandardDaysNumberAll(String.valueOf(StandarDaysNumber(null,null,appUserId)));//总达标天数

        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }
        return personalReport;
    }




    private Long LearnTime(Long appUserId , Date date){
        Long learnTime =0L;
        List<LearnTime> learnTimes = learnTimeService.getLearnTime(appUserId);
        for (LearnTime lt:learnTimes) {
            learnTime+=lt.getTime();
        }
        return learnTime;
    }

    private Long LearnTimeNEW(Long appUserId , Date date){
        Long times = 0L;
        ApiResult apiResult1 = learnTimeService.getUploadLearnTime(appUserId,date);
        List<LearnTime> learnTimes1 = (List<LearnTime>) apiResult1.getData();
        for (LearnTime lt:learnTimes1) {
            times+=lt.getTime();
        }
        return times;
    }


    private Long onlineTime(Long appUserId ,Date date){
        Long learnTime =0L;
        List<OnlineTime> onlineTime = onlineTimeService.findByUserAndDate(appUserId,date);
        for (OnlineTime lt:onlineTime) {
            learnTime+=lt.getTime();
        }
        return learnTime;
    }

    private Long StandarDaysNumber(Date startDate, Date endDate , Long appUserId){
        List<LearnTime> learnTimes1 = learnTimeService.getLearnTimeDate(startDate,endDate,appUserId);
        Long index = 0L;
        for (LearnTime lt:learnTimes1) {
            if(lt.getTime()>=1200000){
                index+=1;
            }
        }
        return index;
    }
}
