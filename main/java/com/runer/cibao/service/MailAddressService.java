package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.MailAddres;
import com.runer.cibao.domain.repository.MailAddressRepository;
import org.springframework.data.domain.Page;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/21
 **/
public interface MailAddressService extends BaseService<MailAddres, MailAddressRepository> {


    /**
     *  根据用户的id查询收货地址
     * @param userId
     * @return
     */
    ApiResult findMailAddressByUserId(Long userId);
    /**
     * 获得收货地址列表
     * @param userId
     * @param page
     * @param limit
     * @return
     */
    Page<MailAddres> findMailAddress(Long userId, Integer page, Integer limit);
    /**
     * 修改收货地址
     * @param id
     * @param phone
     * @param detailAddress
     * @param provinceId
     * @param cityId
     * @param areaId
     * @return
     */
    ApiResult addOrUpdateMailAddress(Long id, Long userId
            , String phone, String detailAddress, String receiveUserName
            , Long provinceId, Long cityId, Long areaId);





}
