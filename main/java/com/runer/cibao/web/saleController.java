package com.runer.cibao.web;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.domain.*;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.*;
import com.runer.cibao.util.NormalUtil;
import com.runer.cibao.util.PowerUtil;
import com.runer.cibao.util.machine.DateMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "sale")
public class saleController {
    @Autowired
    UserLoginService userLoginService;

    @Autowired
    SchoolServivce schoolServivce;

    @Autowired
    SchoolMasterService schoolMasterService;

    @Autowired
    AppUserService appUserService;

    @Autowired
    RedeemCodeService redeemCodeService;

    @Autowired
    PersonalLearnBookService personalLearnBookService;

    @Autowired
    PersonalLearnBooksService personalLearnBooksService;

    @Autowired
    CityService cityService;

    @Autowired
    LearnBookService learnBookService;

    @RequestMapping(value = "data_list")
    public LayPageResult<School> getData(Long agentId, String schoolName, Integer page, Integer limit) {
        Page<School> schoolPage = schoolServivce.findSchoolByAgentId(agentId, schoolName, page, limit);
        Page<School> schools = setSchoolData(schoolPage);
        return NormalUtil.createLayPageReuslt(schools);


    }

    @RequestMapping(value = "month_data")
    public LayPageResult<SaleBean> getMonthData(Long agentId,String rangDate, String province, String city, String schoolName) {
        List<SaleBean> list=new ArrayList<>();
        SaleBean saleBean = new SaleBean();

        long count = personalLearnBookService.findBooksCountByAddressAndDate(agentId,rangDate, province, city, schoolName);
        saleBean.setActiveCount((int)count);
        list.add(saleBean);
        return new LayPageResult(list);

    }

    @RequestMapping(value = "book_data")
    public LayPageResult<LearnBook> getBookData(Long agentId,String rangDate, String province, String city, String bookName,Integer page,Integer limit) {

        Page<LearnBook> learnBooks = learnBookService.findBooks(null,null,null,null,null,bookName,null,null,page,limit);
//       Page<PersonalLearnBook>  personalLearnBookPage= personalLearnBookService.findBooksByAddressAndDate(agentId,rangDate, province, city, schoolName,page,limit);
        return NormalUtil.createLayPageReuslt(learnBooks);

    }




    @RequestMapping(value = "admin_data_list")
    public LayPageResult<School> getAdminData(String schoolName,Long cityId,Long provinceId, Integer page, Integer limit) {
        Page<School> schoolPage = schoolServivce.findSchool(schoolName, cityId, provinceId, null, null, null,
                null, null, null, page, limit);
        Page<School> schools = setSchoolData(schoolPage);
        return NormalUtil.createLayPageReuslt(schools);
    }


    private Page<School> setSchoolData(Page<School> schoolPage) {
        for (School school : schoolPage.getContent()) {
            try {
                List<AppUser> appUsers = appUserService.findAppUserBySchoolId2(school.getUid());
                school.setUserNumber(appUsers.size());
                if(StringUtils.isEmpty(school.getCityId())){
                    continue;
                }
                City city = cityService.findById(school.getCityId());
                school.setProvince(city.getProvince());
                school.setCity(city.getName());
            } catch (SmartCommunityException e) {
                e.printStackTrace();
            }
            SchoolMaster schoolMaster = schoolMasterService.findSchoolMasterBySchoolId(school.getId());
            if (schoolMaster != null) {
                school.setMastername(schoolMaster.getName());
            } else {
                school.setMastername("");
            }

        }
        return schoolPage;
    }




    @RequestMapping(value = "schoolAllBook")
    public LayPageResult<LearnBook> getschoolAllBook(Long schoolId, String bookName, Integer page, Integer limit) {
        try {
            Page<LearnBook> books = learnBookService.findBooks(null,null,null,null,null,bookName,null,null,page,limit);

            for (LearnBook learnBook:books) {
                School school = schoolServivce.findById(schoolId);
                List<AppUser> appUsers = appUserService.findAppUserBySchoolId2(school.getUid());
                Integer ass = 0;
                for (AppUser appUser:appUsers) {
                    List<PersonalLearnBook> personalLearnBooks = personalLearnBookService.getSinglePersonalLearnBook2(appUser.getId(),learnBook.getId());
                    ass += personalLearnBooks.size();
                }
                learnBook.setDownload(ass);
                if(learnBook.getDownLoadNum() == null){
                    learnBook.setDownLoadNum(0L);
                }
            }
            return NormalUtil.createLayPageReuslt(books)  ;
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return  null;
        }

    }

}
