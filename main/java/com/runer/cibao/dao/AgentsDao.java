package com.runer.cibao.dao;

import com.runer.cibao.domain.Agents;
import com.runer.cibao.domain.repository.AgentsRepostory;
import com.runer.cibao.util.JpaQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/9
 **/
@Repository
public class AgentsDao {


    @Autowired
    AgentsRepostory agentsRepostory ;








    /**
     * 姓名 电话 邮箱 性别 生日 注册时间 头像
     */
    public Page<Agents> findAgents (String agentName , String phone , Pageable pageable){


        return   agentsRepostory.findAll((root, criteriaQuery, criteriaBuilder) -> {
                    List<Predicate> predicates = new ArrayList<>();

                    if (!StringUtils.isEmpty(agentName)){
                        predicates.add(criteriaBuilder.like(root.get("name"), JpaQueryUtil.getLikeStrAll(agentName)));
                    }
                    if (!StringUtils.isEmpty(phone)){
                        predicates.add(criteriaBuilder.equal(root.get("phone"), JpaQueryUtil.getLikeStrAll(phone)));
                    }
                    return JpaQueryUtil.createPredicate(predicates,criteriaQuery) ;

                },
                pageable) ;
    }


}
