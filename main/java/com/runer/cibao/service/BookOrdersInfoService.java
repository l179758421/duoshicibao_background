package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.domain.AppUser;
import com.runer.cibao.domain.AppUserOrder;
import com.runer.cibao.domain.AppUserShowInfoBean;
import com.runer.cibao.domain.ClassAppUsersInfoBean;
import org.springframework.data.domain.Page;

import java.util.Date;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/7
 * 购买激活图书的服务；
 **/
public interface BookOrdersInfoService {


    /**
     * 统计经销商的销售详情；
     */

    ApiResult getAgentsSellsInfo(Integer monthInfo, Long agentId);


    /**
     * 获得agents的orders info
     * @return
     */
    ApiResult findOrdersInfo(Long angetsId, Date startDate, Date endDate);

    /**
     * 获得订单的信息；
     * @param schoolId
     * @param classId
     * @param bookId
     * @param startDate
     * @param endDate
     * @return
     */
      Page<AppUserOrder> getBooksCreateOrders(Long schoolId, Long classId, Long bookId, Date startDate, Date endDate, Integer page, Integer limit);

     /**
     *获得学生；
     * @param classInschoolId
     * @param page
     * @param limit
      * @param  schoolId
     * @return
     */
     Page<AppUser> findAppUsers(Long classInschoolId, Long schoolId, String userName, Integer page, Integer limit);


    /**
     * 获得班级的销售详情；
     *
     */
   LayPageResult<ClassAppUsersInfoBean> findClassInfos(String className, Long schoolId, Integer page, Integer limit);


   /*个人详情的展示*/
    /**
     * 个人详情的展示
     * @param schoolId
     * @param classId
     * @param page
     * @param limit
     * @return
     */
   LayPageResult<AppUserShowInfoBean> findAppUserInfos(Long schoolId, Long classId, Integer page, Integer limit);


}
