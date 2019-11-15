package com.runer.cibao.dao;

import com.runer.cibao.Config;
import com.runer.cibao.domain.Message;
import com.runer.cibao.domain.MessageRead;
import com.runer.cibao.domain.repository.MessageReadRepository;
import com.runer.cibao.domain.repository.MessageRepository;
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
 * @Date 2018/7/5
 **/
@Repository
public class MessageDao {

    @Autowired
    MessageRepository messageRepository ;

    @Autowired
    MessageReadRepository messageReadRepository ;



    public long coutMessageNotReaded(Long appUserId ,Integer type){

     return    messageReadRepository.count((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates =new ArrayList<>() ;
            predicates.add(criteriaBuilder.equal(root.get("message").get("msgType"),type)) ;
            predicates.add(criteriaBuilder.equal(root.get("appUser").get("id"),appUserId)) ;
            predicates.add(criteriaBuilder.equal(root.get("state"), Config.NOT_READ));
            return JpaQueryUtil.createPredicate(predicates,criteriaQuery);
        });

    }






    public Page<MessageRead> findMessage(Long appUserId , Integer type ,Integer isRead ,   Pageable pageable){

    return   messageReadRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
        List<Predicate> predicates =new ArrayList<>() ;
        predicates.add(criteriaBuilder.equal(root.get("message").get("msgType"),type)) ;
        predicates.add(criteriaBuilder.equal(root.get("appUser").get("id"),appUserId)) ;
        predicates.add(criteriaBuilder.equal(root.get("state"),isRead));
        return JpaQueryUtil.createPredicate(predicates,criteriaQuery);
      },pageable);
    }

    public Page<Message> findMessageAdmin(Long sendUserId ,String content , Long  classInSchoolId , Integer state , Integer type ,Pageable pageable){
        return messageRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates =new ArrayList<>() ;
            if (sendUserId!=null) {
                predicates.add(criteriaBuilder.equal(root.get("sendUser").get("id"), sendUserId));
            }
            if (classInSchoolId!=null){
                predicates.add(criteriaBuilder.equal(root.get("classInSchool").get("id"),classInSchoolId));
            }
            if (state!=null){
                predicates.add(criteriaBuilder.equal(root.get("pushState'"),state));
            }
            if (type!=null){
                predicates.add(criteriaBuilder.equal(root.get("msgType"),type));
            }
            if (!StringUtils.isEmpty(content)){
                predicates.add(criteriaBuilder.like(root.get("msgContent"), JpaQueryUtil.getLikeStrAll(content)));
            }
            return  JpaQueryUtil.createPredicate(predicates,criteriaQuery) ;

        },pageable);

    }













}
