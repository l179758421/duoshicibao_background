package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.RedeemCode;
import com.runer.cibao.domain.RedeemCodeExcel;
import com.runer.cibao.domain.repository.RedeemCodeRepository;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/7
 **/
public interface RedeemCodeService extends BaseService<RedeemCode,RedeemCodeRepository> {

    /**
     * 充值码的生成 批量
     * @param num
     * @param money
     * @param validity
     * @param des
     * @param userId
     * @return
     */
    ApiResult batchCreateReemCode(Integer num, Integer money, Long validity, String des, Long userId);
    /**
     * 生成一个充值码
     * @param money
     * @param validity
     * @param des
     * @param userId
     * @return
     */
    ApiResult createOneReemCode(Integer money, Long validity, String des, Long userId) ;
    /**
     * 获得激活码
     * @param activeUserId
     * @param activeUserName
     * @param activeSchool
     * @param activeSchoolId
     * @param beginDate
     * @param endDate
     * @param state
     * @param activeTime
     * @param userId
     * @return
     */
    Page<RedeemCode> findRedeemCodes(Long activeUserId, String activeUserName, String activeSchool, Long activeSchoolId,
                                     Date beginDate, Date endDate, Integer state, Date activeTime, Long userId, Integer page, Integer limit
    );
    /**
     * 激活
     * @param
     * @param userId
     * @param schoolUID
     * @return
     */
    ApiResult activeRedeemCode(String reedmeCode, Long userId, String schoolUID);
    /**
     * 批量的激活
     * @param ids
     * @param userId
     * @param schoolID
     * @return
     */
    ApiResult activeRedeeCodeWithdIds(String ids, Long userId, Long schoolID);


    /**
     * 根据充值码数值进行查询
     * @param reedmeCode
     * @return
     */
    ApiResult findByRedeemCode(String reedmeCode);


    /**
     * 充值码转换成excel
     * @param redeemCode
     * @return
     */
    RedeemCodeExcel redeemCodeToExcel(RedeemCode redeemCode) ;


    /**
     * 充值码转换成excel （批量！）
     * @param codes
     * @return
     */
    List<RedeemCodeExcel> codesToExcels(List<RedeemCode> codes) ;


    /**
     * 导出excel
     * @param ids
     * @param fileName
     * @param response
     * @return
     */
     ApiResult exportCodesFor2Excel(String ids, String fileName, HttpServletResponse response) ;

    /**
     * 获得统计信息；
     * @return
     */
     ApiResult countCodes();

    List<RedeemCode> findBySchoolIdAndState(Long schoolId, int state);





}
