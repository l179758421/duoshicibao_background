package com.runer.cibao.web;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.domain.RedeemCode;
import com.runer.cibao.domain.User;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.RedeemCodeService;
import com.runer.cibao.service.UserLoginService;
import com.runer.cibao.util.NormalUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;

/**
 * @Author sww
 * @Date 2019/9/26
 * 充值码Controller
 **/
@RestController
@RequestMapping(value = "redeemCode")
public class RedeeCodeController {


    @Autowired
    RedeemCodeService beanService ;

    @Autowired
    UserLoginService userLoginService ;




    @RequestMapping(value = "index")
    public String schoolIndex(){
        return  "redeemCode/codes_manage" ;
    }

    @RequestMapping(value = "addIndex")
    public String addSchoolIndex(Long id , ModelMap map){
        if (id!=null){
            try {
                map.put("data",beanService.findById(id));
            } catch (SmartCommunityException e) {
                e.printStackTrace();
            }
        }
        return  "redeemCode/create_codes" ;
    }

    @RequestMapping(value = "countCodes")
    @ResponseBody
    public ApiResult countCodes(){
        return  beanService.countCodes();
    }


    @RequestMapping(value = "data_list")
    @ResponseBody
    public LayPageResult<RedeemCode> getDataList(Long activeUserId ,
                                                 String activeUserName,String activeSchool,Long activeSchoolId ,
    String  rangDate   ,Integer state ,Date activeTime ,Long userId,Integer page ,Integer limit
                                     ){
        Date startDate =null ;
        Date endDate =null ;
        if (!StringUtils.isEmpty(rangDate)){
            String[] dateDatas = rangDate.split(" ");
            try {
                startDate=DateUtils.parseDate(dateDatas[0]+" 00:00:00","yyyy-MM-dd HH:mm:ss") ;
            endDate =DateUtils.parseDate(dateDatas[2]+" 24:00:00","yyyy-MM-dd HH:mm:ss") ;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Page<RedeemCode> pageResult = beanService.findRedeemCodes(activeUserId, activeUserName, activeSchool, activeSchoolId, startDate, endDate, state, activeTime, userId, page, limit);
        return NormalUtil.createLayPageReuslt(pageResult) ;
    }


    @RequestMapping(value = "addOrUpdateData")
    public ApiResult addOrUpdateData(Integer num , Integer money , Long validity , String des, HttpServletRequest req){
        User currentUser = userLoginService.getCurrentUser(req);
        if (currentUser!=null){
           Long userId =currentUser.getId() ;
        return beanService.batchCreateReemCode(num,money,validity,des,userId);
       }
       return  new ApiResult(ResultMsg.NOT_FOUND,null) ;
    }

    @RequestMapping("deleteByIds")
    public ApiResult deleteByIds(String ids){

        StringBuilder delteIds =new StringBuilder() ;
        if (!StringUtils.isEmpty(ids)){
            String[] idArray = ids.split(",");
            for (String s : idArray) {
             if (!is24Inner(Long.valueOf(s))){
                 delteIds.append(s);
                 delteIds.append(",");
             }
            }
        }
        if (StringUtils.isEmpty(delteIds.toString())){
            return  new ApiResult("充值码已被激活或未激活但超过24小时,不允许删除!");
        }
//        return  NormalUtil.deleteByIds(beanService,delteIds.toString()) ;
        return  NormalUtil.deleteByIds(beanService,ids) ;
    }


    @RequestMapping("deleteById")
    public ApiResult deleteById(Long id){
        if (is24Inner(id)){
            return  new ApiResult("充值码已被激活或未激活但超过24小时,不允许删除!");
        }
        return  NormalUtil.deleteById(beanService,id);
    }

    @RequestMapping(value = "exportExcel")
    public ApiResult  exportsExcle(String ids ,String fileName, HttpServletResponse response){
        return beanService.exportCodesFor2Excel(ids, fileName, response);
    }


    private boolean  is24Inner(Long reedemId ){
        ApiResult apiResult = beanService.findByIdWithApiResult(reedemId) ;
        if (apiResult.isSuccess()) {
            RedeemCode redeemCode = (RedeemCode) apiResult.getData();
            Date createTime =redeemCode.getCreateTime();
            //未激活状态 24h 内可以删除；
            if(redeemCode.getState() == 1 || redeemCode.getState() == 2 ){
                return true ;
            }
            if(redeemCode.getState() == 0 && DateUtils.addHours(createTime,24).before(new Date())){
                return  true ;
            };
        }
        return  false ;
    }



}
