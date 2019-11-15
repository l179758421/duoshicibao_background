package com.runer.cibao.service.impl;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.runer.cibao.Config;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.RedeemCodeDao;
import com.runer.cibao.domain.*;
import com.runer.cibao.domain.repository.RedeemCodeRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.*;
import com.runer.cibao.util.ExcelUtil;
import com.runer.cibao.util.NormalUtil;
import com.runer.cibao.util.machine.CodeRandomMachine;
import com.runer.cibao.util.page.PageableUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;

import static com.runer.cibao.exception.ResultMsg.SCHOOL_ID_IS_NULL;
import static com.runer.cibao.exception.ResultMsg.SUCCESS;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/7
 **/

@Service
public class RedeemCodeServiceImpl extends BaseServiceImp<RedeemCode,RedeemCodeRepository> implements RedeemCodeService {

    @Autowired
    private RedeemCodeDao redeemCodeDao ;

    @Autowired
    private CodeRandomMachine codeRandomMachine ;

    @Autowired
    private RedeemCodeRepository redeemCodeRepository ;

    @Autowired
    AppUserAccountService appUserAccountService ;

    @Autowired
    AppUserOrderService appUserOrderService ;

    @Autowired
    AppUserService appUserService ;

    @Autowired
    SchoolServivce schoolServivce ;

    @Autowired
    IntegralService integralService;


    @Value("${web.upload-path}")
    private String upload_path;

    @Value("${web.upload-basePath}")
    private String upload_base_path;


    @Override
    public ApiResult batchCreateReemCode(Integer num, Integer money, Long validity, String des, Long userId) {

        if (userId==null){
            return  new ApiResult(ResultMsg.USER_ID_IS_NOT_ALLOWED_NULL,null);
        }
        if (num==null){
            return  new ApiResult(ResultMsg.NUMBER_IS_NULL_FOR_REDEEMCODE,null) ;
        }


        List<RedeemCode> redeemCodes =new ArrayList<>() ;

        for (int i = 0; i <num ; i++) {

            RedeemCode redeemCode =new RedeemCode() ;

            String codeNum = codeRandomMachine.productCode11() ;

            redeemCode.setCodeMoney(money);
            redeemCode.setCodeNum(codeNum.toUpperCase());
            redeemCode.setState(Config.CODE_NOT_USE);
            redeemCode.setCreateTime(new Date());
            redeemCode.setTermOfvalidity(Math.toIntExact(validity));
            redeemCode.setDes(des);
            User user =new User() ;
            user.setId(userId);
            redeemCode.setUpLoadUser(user);

            redeemCodes.add(redeemCode);

        }

       redeemCodes = redeemCodeRepository.saveAll(redeemCodes);

       return  new ApiResult(SUCCESS,redeemCodes);
    }



    @Override
    public ApiResult createOneReemCode(Integer money, Long validity, String des, Long userId) {

        RedeemCode redeemCode =new RedeemCode() ;

        String codeNum = codeRandomMachine.productCode() ;

        redeemCode.setCodeMoney(money);
        redeemCode.setCodeNum(codeNum);
        redeemCode.setState(Config.CODE_NOT_USE);
        redeemCode.setCreateTime(new Date());
        redeemCode.setTermOfvalidity(Math.toIntExact(validity));
        redeemCode.setDes(des);

        if (userId==null){
            return  new ApiResult(ResultMsg.USER_ID_IS_NOT_ALLOWED_NULL,null);
        }

        User user =new User() ;
        user.setId(userId);
        redeemCode.setUpLoadUser(user);

        try {
            redeemCode =  save(redeemCode);
            return  new ApiResult(SUCCESS,redeemCode);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return  new ApiResult(e.getResultMsg(),null) ;
        }

    }

    @Override
    public Page<RedeemCode> findRedeemCodes(Long activeUserId, String activeUserName, String activeSchool,
                                            Long activeSchoolId, Date beginDate, Date endDate, Integer state,
                                            Date activeTime, Long userId,Integer page ,Integer limit) {
        Page<RedeemCode> pages = redeemCodeDao.findRedeemCodes(activeUserId, activeUserName, activeSchool, activeSchoolId,
                beginDate, endDate, state, activeTime, userId, PageableUtil.basicPage(page, limit));
        if (!ListUtils.isEmpty(pages.getContent())){
            pages.getContent().forEach(redeemCode -> {
                redeemCode.isTimeOut();
            });
            r.saveAll(pages.getContent());
        }
        return  pages ;
    }

    //激活  即充值；
    @Override
    public ApiResult activeRedeemCode(String code, Long userId, String  schoolUID) {

        if (StringUtils.isEmpty(code)){
            return  new ApiResult(ResultMsg.CODE_IS_NULL,null) ;
        }
        if (code.length()!=11){
            return  new ApiResult(ResultMsg.CODE_IS_NOT_ILLELALL,null) ;
        }
        if (schoolUID==null){
            return  new ApiResult(SCHOOL_ID_IS_NULL,null) ;
        }
        //校验学校
       ApiResult schoolResult  =  schoolServivce.findSchoolByUID(schoolUID) ;
        if (schoolResult.isFailed()){
            return  new ApiResult("学校不存在") ;
        }
        School school = (School) schoolResult.getData();
        //检验user;
        ApiResult userResult =appUserService.findByIdWithApiResult(userId) ;
        if (userResult.isFailed()){
            return  new ApiResult("用户不存在");
        }
        AppUser appUser = (AppUser) userResult.getData();
        //检验code ；
        ApiResult codeResult =  findByRedeemCode(code) ;
        if (codeResult.isFailed()){
          return  new ApiResult(ResultMsg.CODE_IS_NOT_ILLELALL,null);
        }
        RedeemCode redeemCode = (RedeemCode) codeResult.getData();

       if (Config.CODE_NOT_USE!=redeemCode.getState()){
           return  new ApiResult("充值码已过期或已使用");
       }
        Date endDate =DateUtils.addDays(redeemCode.getCreateTime(),redeemCode.getTermOfvalidity());
        /**
         * 若是没有过期的情况下；
         */
        if (endDate.after(new Date())){
            appUserAccountService.findAccountByUserId(userId) ;

            //激活；!!!!!!!
            redeemCode.setState(Config.CODE_ACTIVE);
            redeemCode.setActiveTime(new Date());
            redeemCode.setUser(appUser);

            //设置学校;
            redeemCode.setSchool(school);


            //order的产生
            ApiResult orderResult = appUserOrderService.createOrder(userId, Config.ORDER_TYPE_RECHARGE, "充值卡充值", "充值", redeemCode.getId(), redeemCode.getCodeMoney());

            if (orderResult.isSuccess()){
                //充值成功获得积分,修改相应级别
                integralService.addRechargeIntegral(userId);
                AppUserOrder appUserOrder = (AppUserOrder) orderResult.getData();
                redeemCode.setAppUserOrder(appUserOrder);
                redeemCode =  r.saveAndFlush(redeemCode);
                return  new ApiResult(SUCCESS,redeemCode) ;
            }else{
                return  orderResult ;
            }

        }else{

            String startTime =DateFormatUtils.format(redeemCode.getCreateTime(),"yyyy年MM月dd日 hh时mm分ss秒");
            String endTime =DateFormatUtils.format(endDate,"yyyy年MM月dd日 hh时mm分ss秒");
            HashMap<String,String> result =new HashMap<>() ;
            result.put("startTime",startTime) ;
            result.put("endTime",endTime) ;

           return new ApiResult(ResultMsg.CODE_TIME_OUT,result);
        }


    }


    //todo 激活
    @Override
    public ApiResult activeRedeeCodeWithdIds(String ids, Long userId, Long schoolID) {
        return null;
    }

    @Override
    public ApiResult findByRedeemCode(String reedmeCode) {

       RedeemCode redeemCode = r.findRedeemCodeByCodeNum(reedmeCode);

       if (redeemCode==null){
           return  new ApiResult(ResultMsg.NOT_FOUND,null) ;
       }

        return new ApiResult(SUCCESS,redeemCode);
    }

    @Override
    public RedeemCodeExcel redeemCodeToExcel(RedeemCode redeemCode) {


        RedeemCodeExcel redeemCodeExcel =new RedeemCodeExcel() ;
        redeemCodeExcel.setId(redeemCode.getId());
        redeemCodeExcel.setCodeNum(redeemCode.getCodeNum());
        redeemCodeExcel.setDes(redeemCode.getDes());
        redeemCodeExcel.setCodeMoney(redeemCode.getCodeMoney());

        if (redeemCode.getState()== Config.CODE_OUT_TIME){
            redeemCodeExcel.setState("已过期");
        }else if (redeemCode.getState()== Config.CODE_ACTIVE){
            redeemCodeExcel.setState("已激活");
        }else {
            redeemCodeExcel.setState("未激活");
        }
        if (redeemCode.getUser()!=null){
            redeemCodeExcel.setUser(redeemCode.getUser().getName());
        }else{
            redeemCodeExcel.setUser("");
        }
        if (redeemCode.getSchool()!=null) {
            redeemCodeExcel.setSchool(redeemCode.getSchool().getName());
        }else{
            redeemCodeExcel.setSchool("");
        }
        redeemCodeExcel.setTermOfvalidity(redeemCode.getTermOfvalidity()+"天");
        redeemCodeExcel.setCreateTime(DateFormatUtils.format(redeemCode.getCreateTime(),"yyyy-MM-dd hh:mm:ss"));

        if (redeemCodeExcel.getActiveTime()!=null) {
            redeemCodeExcel.setActiveTime(DateFormatUtils.format(redeemCode.getActiveTime(),"yyyy-MM-dd hh:mm:ss"));
        }else{
            redeemCodeExcel.setActiveTime("");
        }
        redeemCodeExcel.setIsOutTime("");
        return  redeemCodeExcel ;
    }

    @Override
    public List<RedeemCodeExcel> codesToExcels(List<RedeemCode> codes) {

        List<RedeemCodeExcel> redeemCodeExcels =new ArrayList<>() ;

        codes.forEach(redeemCode -> {
            redeemCodeExcels.add(redeemCodeToExcel(redeemCode));
        });


        return redeemCodeExcels;
    }

    @Override
    public ApiResult exportCodesFor2Excel(String ids, String fileName , HttpServletResponse response) {
        String  path=upload_path+fileName;
        String  basePath=upload_base_path+fileName;
        try {
         List<RedeemCode >  redeemCodes =  findByIds(ids) ;
            List<RedeemCode >  redeemCodes2 = new ArrayList<>() ;
            String[] split = ids.split(",");
            for(int i=0;i<split.length;i++){
                for (RedeemCode redeemCode : redeemCodes) {
                    if(split[i].equals(String.valueOf(redeemCode.getId()))){
                        redeemCodes2.add(redeemCode);
                        continue;
                    }
                }
            }
         List<RedeemCodeExcel> exportData = codesToExcels(redeemCodes2);
         OutputStream out = new FileOutputStream(path);
         ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
         Sheet sheet1 = new Sheet(1, 0, RedeemCodeExcel.class);
         sheet1.setSheetName(fileName);
         writer.write(exportData, sheet1);
         writer.finish();
        } catch (Exception e) {
            e.printStackTrace();
            return  new ApiResult(ResultMsg.OS_ERROR,null ) ;
        }
        return  new ApiResult(SUCCESS,basePath) ;

    }

    @Override
    public ApiResult countCodes() {
        List<RedeemCode> judgeDatas = redeemCodeDao.findToJugeTimeOut();
        List<RedeemCode> toSave =new ArrayList<>() ;
        for (RedeemCode judgeData : judgeDatas){
            if (judgeData.isTimeOut()){
                toSave.add(judgeData);
            }
        }
        r.saveAll(toSave) ;

        long allCount =redeemCodeDao.count(null) ;
        long activeCount=redeemCodeDao.count(Config.CODE_ACTIVE);
        long outTimeCount =redeemCodeDao.count(Config.CODE_OUT_TIME);
        long notUser =allCount-activeCount-outTimeCount ;

        Map<String, Object> datas = NormalUtil.generateMapData(data -> {
            data.put("all",allCount) ;
            data.put("active",activeCount) ;
            data.put("outTime",outTimeCount) ;
            data.putIfAbsent("notUse",notUser) ;
        });
        return new ApiResult(SUCCESS,datas);
    }

    @Override
    public List<RedeemCode> findBySchoolIdAndState(Long schoolId, int state) {

        return r.findRedeemCodesBySchoolIdAndState(schoolId,state);
    }
}
