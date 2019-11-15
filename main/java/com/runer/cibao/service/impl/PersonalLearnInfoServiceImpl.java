package com.runer.cibao.service.impl;

import com.runer.cibao.Config;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.PersonalLearnInfoDao;
import com.runer.cibao.domain.*;
import com.runer.cibao.domain.repository.PersonlLearnInfoRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.*;
import com.runer.cibao.util.NormalUtil;
import com.runer.cibao.util.machine.DateMachine;
import com.runer.cibao.util.page.PageableUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.runer.cibao.Config.RANKING_COUNTRY_TYPE;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/9/5
 **/
@Service
public class PersonalLearnInfoServiceImpl extends BaseServiceImp<PersonlLearnInfoBean, PersonlLearnInfoRepository> implements PersonalLearnInfoService {

    @Autowired
    PersonalLearnInfoDao personalLearnInfoDao;
    @Autowired
    AppUserService appUserService ;
    @Autowired
    SchoolServivce schoolService ;
    @Autowired
    MedalsService medalsService ;
    @Autowired
    ProvinceService provinceService ;
    @Autowired
    SchoolServivce schoolServivce ;
    @Autowired
    ClassInSchoolService classInSchoolService ;
    @Autowired
    PersonlLearnInfoRepository personlLearnInfoRepository;
    @Autowired
    DateMachine dateMachine ;



    @Override
    public ApiResult generateOneLearnInfo(Long userId, Date date) {

        if (date==null){
            return  new ApiResult("日期不能为空");
        }
        ApiResult userReuslt =appUserService.findByIdWithApiResult(userId) ;
        if (userReuslt.isFailed()){
            return  new ApiResult("用户不存在");
        }
        List<PersonlLearnInfoBean> datas = personalLearnInfoDao.find(userId, date);
        if (ListUtils.isEmpty(datas)){
            PersonlLearnInfoBean bean =new PersonlLearnInfoBean() ;
            bean.setAppUser((AppUser) userReuslt.getData());
            bean.setDate(date);
            r.saveAndFlush(bean) ;
            return  new ApiResult(ResultMsg.SUCCESS,bean) ;
        }else {
            return  new ApiResult(ResultMsg.SUCCESS,datas.get(0)) ;
        }
    }

    @Override
    public ApiResult findOneLearnInfo(Long userId, Date startDate, Date endDate) {
        ApiResult userReuslt =appUserService.findByIdWithApiResult(userId) ;
        if (userReuslt.isFailed()){
            return  new ApiResult("用户不存在");
        }
        List<PersonlLearnInfoBean> datas = personalLearnInfoDao.findByRange(userId, startDate,endDate);
       if(datas.size()>0){
           return  new ApiResult(ResultMsg.SUCCESS,datas.get(0));
       }else{
           return  new ApiResult(ResultMsg.SUCCESS,null) ;
       }
    }



    @Override
    public ApiResult findOneLearnInfoNew(Long userId, Date startDate, Date endDate) {
        ApiResult userReuslt =appUserService.findByIdWithApiResult(userId) ;
        if (userReuslt.isFailed()){
            return  new ApiResult("用户不存在");
        }
        List<PersonlLearnInfoBean> datas = personalLearnInfoDao.findByRange(userId, startDate,endDate);
           return  new ApiResult(ResultMsg.SUCCESS,datas);
    }




    @Override
    public ApiResult findOneLearnInfo30(Long userId, Date startDate, Date endDate) {

        ApiResult userReuslt =appUserService.findByIdWithApiResult(userId) ;
        if (userReuslt.isFailed()){
            return  new ApiResult("用户不存在");
        }
        List<PersonlLearnInfoBean> datas = personalLearnInfoDao.findByRange(userId, startDate,endDate);
        if(datas.size()>0){
            return  new ApiResult(ResultMsg.SUCCESS,datas) ;
        }else{
            return  new ApiResult(ResultMsg.SUCCESS,null) ;
        }
    }



    @Override
    public ApiResult findOneLearn(Long userId) {
        List<PersonlLearnInfoBean> personlLearnInfoBean = personalLearnInfoDao.find(userId,null);
        return new ApiResult(ResultMsg.SUCCESS,personlLearnInfoBean);
    }


    public List<PersonlLearnInfoBean> findByClassUserLearn(Long classId, Date startTime , Date endTime){
        List<PersonlLearnInfoBean> personlLearnInfoBeans = new ArrayList<PersonlLearnInfoBean>();
        if(classId != null){
//            personlLearnInfoBeans = personlLearnInfoRepository.findByLearnUser(classId,startTime,endTime);
            personlLearnInfoBeans = personalLearnInfoDao.findByLearnUser(classId,startTime,endTime);
        }
        return personlLearnInfoBeans;
    }

    /**
     * 个人的单词数量的排名；
     * @param appUserId  userId
     * @param type  school country province ;
     * @param date  date ;
     * @return
     */
    @Override
    public ApiResult getOnePersonalRanking(Long appUserId, Integer type, Date date) {

        ApiResult oneResult = generateOneLearnInfo(appUserId, date);
        PersonlLearnInfoBean infoBean = (PersonlLearnInfoBean) oneResult.getData();

        Long time =   infoBean.getLearnTime() ;

        String  schoolId =infoBean.getAppUser().getUid();
        Long provinceId =infoBean.getAppUser().getProvinceId() ;

        long allWords =  infoBean.getAllWords();
        long  rankingTime =  personalLearnInfoDao.countOnePersonRankingWord(true,date,type,null,time,provinceId,schoolId);
        long  rankingWords =personalLearnInfoDao.countOnePersonRankingWord(false,date,type,allWords,null,provinceId,schoolId);

        Map<String, Object> results = NormalUtil.generateMapData(data -> {
            data.put("time",rankingTime) ;
            data.put("words",rankingWords);
        });
        return new ApiResult(ResultMsg.SUCCESS,results);
    }


    @Override
    public ApiResult getRankingInfo(Long appUserId, Integer topNum, Date date , Integer type ) {
        if (topNum<1){
            return  new ApiResult("topNum必须大于一");
        }



        //初始化输出的数列
        List<PersonlLearnInfoBean> infos =new ArrayList<>() ;


        //个人相关的情况；
        ApiResult oneResult = generateOneLearnInfo(appUserId, date);
        PersonlLearnInfoBean infoBean = (PersonlLearnInfoBean) oneResult.getData();
        String  schoolId =infoBean.getAppUser().getSchoolId();
        School school =null ;
        ApiResult schoolResult = schoolService.findSchoolByUID(schoolId);
        if (schoolResult.isSuccess()){
            school = (School) schoolResult.getData();
        }
        long allWords =  infoBean.getAllWords();
        Long provinceId  =null ;
        if (school!=null) {
             provinceId = school.getProvinceId();
        }


        long  rankingWords =personalLearnInfoDao.countOnePersonRankingWord(false,date,type,infoBean.getAllWords(),null,provinceId,schoolId);
        if (rankingWords<=10){
            return  new ApiResult(ResultMsg.SUCCESS,infos) ;
        }


        List<PersonlLearnInfoBean> lefts = personalLearnInfoDao.getLeftOrRight(true, schoolId, provinceId, type, date, allWords, PageableUtil.basicPage(0, topNum));
        List<PersonlLearnInfoBean> rights = personalLearnInfoDao.getLeftOrRight(false, schoolId, provinceId, type, date, allWords, PageableUtil.basicPage(0, topNum));


        for (int i = 0; i < lefts.size(); i++) {
            PersonlLearnInfoBean infoBeanItem =lefts.get(i) ;
            long  rank =personalLearnInfoDao.countOnePersonRankingWord(false,date,type,infoBeanItem.getAllWords(),null,provinceId,schoolId);
            PersonlLearnInfoBean info =new PersonlLearnInfoBean();
            info.setRank((int) rank);
            info.setName(infoBeanItem.getAppUser().getName());
            info.setAllWords(infoBeanItem.getAllWords());
            infos.add(info);
        }

        //自己------appuserID对应的数据；
        infoBean.setRank((int) rankingWords);
        if (infoBean.getAppUser()!=null) {
            infoBean.setName(infoBean.getAppUser().getName());
        }
        infoBean.setIndex(true);
        infos.add(infoBean) ;

        for (int i = 0; i < rights.size(); i++) {
            PersonlLearnInfoBean infoBeanItem =rights.get(i) ;
            long  rank =personalLearnInfoDao.countOnePersonRankingWord(false,date,type,infoBeanItem.getAllWords(),null,provinceId,schoolId);
            PersonlLearnInfoBean info =new PersonlLearnInfoBean() ;
            info.setRank((int) rank);
            info.setName(infoBeanItem.getAppUser().getName());
            info.setAllWords(infoBeanItem.getAllWords());
            infos.add(info);
        }
        return new ApiResult(ResultMsg.SUCCESS,infos);
    }

    @Override
    public ApiResult getRankingALl(String  schooId  , Long provinceId , Integer topNum, Date date, Integer type) {
        if (type==null){
            type =RANKING_COUNTRY_TYPE;
        }
        if (type== Config.RANKING_SCHOOL_TYPE){
            if (StringUtils.isEmpty(schooId)){
                return  new ApiResult(ResultMsg.SUCCESS,new ArrayList<>()) ;
            }
        }
        if (type== Config.RANKING_PROVINCE_TYPE){
            if (provinceId==null){
                return  new ApiResult(ResultMsg.SUCCESS,new ArrayList<>()) ;
            }
        }
        if (topNum==null){
            topNum =10;
        }
        String schoolUid ="";
        if (!StringUtils.isEmpty(schooId)){
            ApiResult schoolResult  = schoolService.findSchoolByUID(schooId);
            if (schoolResult.isFailed()){
                return new ApiResult("学校不存在");
            }
            schoolUid =((School)schoolResult.getData()).getUid();
        }
        List<PersonlLearnInfoBean> personals = personalLearnInfoDao.getTopRanking(schoolUid, provinceId, type, date, PageableUtil.basicPage(0, topNum));;
        for (int i = 0; i < personals.size(); i++) {
            PersonlLearnInfoBean personal =personals.get(i) ;
            AppUser appUser =personal.getAppUser();
            if (appUser!=null){
                personal.setName(appUser.getName());
                long rank = personalLearnInfoDao.countOnePersonRankingWord(false, date, type, personal.getAllWords(), null, appUser.getProvinceId(), appUser.getSchoolId());
                personal.setRank((int) rank);
            }
        }
        return   new ApiResult(ResultMsg.SUCCESS,personals);
    }

    /**
     * 定时任务，用于统计  每天的2点半进行统计一次；
     * @return
     */
    @Override
    @Scheduled(cron = "0 30 02 * * ?")
    public ApiResult ditrubePersonsHornor() {
        Date date =DateUtils.addDays(new Date(),-1) ;
        Date[] dates = dateMachine.getOneDayTimes(date);
        List<Medals> medals =new ArrayList<>() ;
        //全国的排名；
        List<PersonlLearnInfoBean> countryTops = personalLearnInfoDao.getTop3(null, null, null,dates[0],dates[1]);

        if (!ListUtils.isEmpty(countryTops)){
            for (int i = 0; i < countryTops.size(); i++) {
                addMedals(countryTops,i,0,medals);
            }
        }
        //全省的排名；
        for (Province province : provinceService.findAll()) {
            List<PersonlLearnInfoBean> provinceInfos =personalLearnInfoDao.getTop3(province.getId(), null, null,dates[0],dates[1]);
            for (int i = 0; i < provinceInfos.size(); i++) {
                addMedals(provinceInfos,i,1,medals);
            }
        }
        //全校的排名；
        for (School school : schoolService.findAll()) {
            List<PersonlLearnInfoBean> schoolInfos =personalLearnInfoDao.getTop3(null, school.getId(), null,dates[0],dates[1]);
            for (int i = 0; i < schoolInfos.size(); i++) {
                addMedals(schoolInfos,i,2,medals);
            }
        }
        //全班的排名；
        for (ClassInSchool classInSchool : classInSchoolService.findAll()) {
            List<PersonlLearnInfoBean> classInSchoolInfos =personalLearnInfoDao.getTop3(null, null, classInSchool.getId(),dates[0],dates[1]);
            for (int i = 0; i < classInSchoolInfos.size(); i++) {
                addMedals(classInSchoolInfos,i,3,medals);
            }
        }
        try {
            medalsService.saveOrUpdate(medals) ;
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }
        return new ApiResult(ResultMsg.SUCCESS,null);
    }


    private void addMedals(List<PersonlLearnInfoBean>countryTops , int  i, int type , List<Medals> medals){

        Medals medalbean =new Medals() ;
        PersonlLearnInfoBean co = countryTops.get(i) ;

        AppUser user =co.getAppUser() ;
        medalbean.setAppUser(user);
        medalbean.setCreateDate(new Date());
        medalbean.setOrderIndex(i+1);
        String firstName = "国";


        if (type==0){
            firstName ="国";
        }else if (type==1){
            firstName="省";
        }else if (type==2){
            firstName ="校";
        }else if (type==3){
            firstName ="班";
        }
        //金牌
        if (i==0){
            medalbean.setDes(firstName+"金");
        }else if (i==1){
            medalbean.setDes(firstName+"银");
        }else if (i==2){
            medalbean.setDes(firstName+"铜");
        }
        medals.add(medalbean) ;
    }


}
