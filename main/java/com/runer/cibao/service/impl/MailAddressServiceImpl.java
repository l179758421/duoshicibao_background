package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.MailAddressDao;
import com.runer.cibao.domain.*;
import com.runer.cibao.domain.repository.MailAddressRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.AppUserService;
import com.runer.cibao.service.AreaService;
import com.runer.cibao.service.MailAddressService;
import com.runer.cibao.util.page.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;
import org.thymeleaf.util.StringUtils;

import java.util.Date;

import static com.runer.cibao.exception.ResultMsg.*;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/21
 **/
@Service
public class MailAddressServiceImpl extends BaseServiceImp<MailAddres, MailAddressRepository> implements MailAddressService {


    @Autowired
    private MailAddressDao mailAddressDao ;


    @Autowired
    private AppUserService appUserService ;

    @Autowired
    private AreaService areaService ;

    @Override
    public ApiResult findMailAddressByUserId(Long userId) {

        try {
            appUserService.findById(userId);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return  new ApiResult(ResultMsg.USER_IS_NOT_EXIST,null) ;
        }

        Page<MailAddres> mailPage = mailAddressDao.findMailAddress(userId, PageableUtil.basicPage(1, 10));
        if (!ListUtils.isEmpty(mailPage.getContent())){
            return  new ApiResult(ResultMsg.SUCCESS,mailPage.getContent().get(0)) ;
        }
        return new ApiResult(ResultMsg.SUCCESS,null);
    }

    @Override
    public Page<MailAddres> findMailAddress(Long userId, Integer page, Integer limit) {

        return mailAddressDao.findMailAddress(userId,PageableUtil.basicPage(page,limit));
    }

    @Override
    public ApiResult addOrUpdateMailAddress(Long id, Long userId , String phone, String detailAddress, String receiveUserName,
                                            Long provinceId, Long cityId, Long areaId) {
        MailAddres mailAddres =new MailAddres() ;
        //id 为空的情况下,不能够再次进行添加，只能够存在一个收货地址
        if (id==null){
            ApiResult address = findMailAddressByUserId(userId);
            if (address.getData()!=null){
                return  new ApiResult(ADDRESS_IS_EXISTED_FOR_THIS_USER,null);
            }
            mailAddres.setCreateTime(new Date());
        }else{
            try {
                mailAddres =findById(id);
            } catch (SmartCommunityException e) {
                e.printStackTrace();
                return  new ApiResult(e.getResultMsg(),null) ;
            }
        }



        mailAddres.setId(id);
        /**
         * 详细地址
         */
        if (StringUtils.isEmpty(detailAddress)){
            return  new ApiResult(DETAIL_ADDRESS_IS_NULL,null) ;
        }

        mailAddres.setDetailAddress(detailAddress);

        /**
         * 关联用户
         */

        try {
            AppUser appUser =appUserService.findById(userId);
            mailAddres.setAppUser(appUser);

        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return  new ApiResult(e.getResultMsg(),null) ;
        }


        /**
         * 手机
         */
        if (StringUtils.isEmpty(phone)){
            return  new ApiResult(PHONE_NUMBER_IS_NULL,null) ;
        }
        mailAddres.setPhoneNum(phone);

        /**
         * 设置地区
         */
        String addreeDetail ="";

        if (provinceId!=null){
            Province province =areaService.getProvince(provinceId);
            mailAddres.setProvinceId(provinceId);
            if (province!=null){
                addreeDetail +=province.getName();
            }
        }else{
            return  new ApiResult(ADDRESS_ID_IS_NULL,null);
        }

        if (cityId!=null){
            City city =areaService.findCityById(cityId);
            mailAddres.setCityId(cityId);
            if (city!=null){
                addreeDetail+=city.getName() ;
            }
        } else{
            return  new ApiResult(ADDRESS_ID_IS_NULL,null);
        }

        if (areaId!=null){
            Area area= areaService.findAreaById(areaId);
            mailAddres.setAreaId(areaId);
            if (area!=null){
                addreeDetail+=area.getName();
            }
        }else{
            return  new ApiResult(ADDRESS_ID_IS_NULL,null);
        }

        addreeDetail+=":"+mailAddres.getDetailAddress();


        /**
         * 地址quancheng
         */
        mailAddres.setAllAddress(addreeDetail);


        /**
         * 收货人
         */
        if (StringUtils.isEmpty(receiveUserName)){
            return  new ApiResult(RECEIVED_NAME_IS_NULL,null) ;
        }

        mailAddres.setReceiveUserName(receiveUserName);

        try {
            mailAddres =saveOrUpdate(mailAddres);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return  new ApiResult(e.getResultMsg(),null) ;
        }

        return new ApiResult(SUCCESS,mailAddres);
    }
}
