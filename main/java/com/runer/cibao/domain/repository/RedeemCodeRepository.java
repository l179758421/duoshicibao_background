package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.RedeemCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/7
 **/
public interface RedeemCodeRepository extends JpaRepository<RedeemCode,Long> ,JpaSpecificationExecutor<RedeemCode> {

     RedeemCode findRedeemCodeByCodeNum(String codeNum) ;
     List<RedeemCode> findRedeemCodesBySchoolIdAndState(Long schoolId, int state);


}
