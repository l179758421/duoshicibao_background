package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.AppUserOrder;
import com.runer.cibao.domain.repository.AppUserOrderRepository;
import org.springframework.data.domain.Page;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/27
 **/
public interface AppUserOrderService extends BaseService<AppUserOrder, AppUserOrderRepository>{


    ApiResult createOrder(Long userId, Integer type, String title, String des, Long relatedId, Integer changeNum) ;

    ApiResult updateOrder(Long id, String title, String des, Long relatedId, Integer ChangeNum);

    Page<AppUserOrder> findOrders(String title, Integer type, Long userId, Integer page, Integer limit) ;

    ApiResult deleteOrder(Long orderId) ;



}
