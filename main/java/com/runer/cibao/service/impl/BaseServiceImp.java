package com.runer.cibao.service.impl;


import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.BaseBean;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.BaseService;
import com.runer.cibao.util.JpaQueryUtil;
import com.runer.cibao.util.machine.IdsMachine;
import com.runer.cibao.util.page.PageableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.runer.cibao.exception.ResultMsg.ENTITY_ID_NOT_EXISTS;
import static com.runer.cibao.exception.ResultMsg.SUCCESS;


/**
 * @Author szhua
 * 2018-04-19
 * 9:28
 * @Descriptionsbaby_photos== BaseServiceImp
 **/
public class BaseServiceImp<T extends BaseBean, R extends JpaRepository<T, Long> & JpaSpecificationExecutor<T>> implements BaseService<T, R> {


    public Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected R r;

    public R getR() {
        return r;
    }

    @Override
    public T findById(Long id) throws SmartCommunityException {
           if (!r.findById(id).isPresent()){
               throw  new SmartCommunityException(ENTITY_ID_NOT_EXISTS);
           }
           return r.findById(id).get();
    }

    @Override
    public boolean deleteById(Long id) throws SmartCommunityException {
            if (!r.findById(id).isPresent()){
                throw  new SmartCommunityException(ENTITY_ID_NOT_EXISTS);
            }
            r.deleteById(id);
            return true;

    }
    @Override
    public void deleteByIds(String ids) throws SmartCommunityException {
       try{
           List<T> ts = r.findAll((root, criteriaQuery, criteriaBuilder) -> {
               List<Predicate> predicates = new ArrayList<>();
               predicates.add(root.get("id").in(ids.split(",")));
               return JpaQueryUtil.createPredicate(predicates, criteriaQuery);
           });
           r.deleteInBatch(ts);
       }catch (Exception e){
           e.printStackTrace();
           throw  new SmartCommunityException(ResultMsg.IDS_IS_NOT_ILLEGAL) ;
       }
    }

    @Override
    public List<T> findAll() {
        return r.findAll();
    }

    @Override
    public Page<T> findByPage(Integer page, Integer rows) {
        return r.findAll(PageableUtil.basicPage(page, rows));
    }

    @Override
    public T saveOrUpdate(T t) throws SmartCommunityException {
        return r.saveAndFlush(t);
    }

    @Override
    public List<T> saveOrUpdate(List<T> datas) throws SmartCommunityException {
        return r.saveAll(datas);
    }

    @Override
    public T save(T t) throws SmartCommunityException {
        return r.save(t);
    }

    @Override
    public T update(T t) throws SmartCommunityException {
        return r.saveAndFlush(t);
    }

    @Override
    public List<T> findByIds(String ids) throws SmartCommunityException {

        try{
            List<T> ts = r.findAll((root, criteriaQuery, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(root.get("id").in(new IdsMachine().deparseIds(ids)));
                return JpaQueryUtil.createPredicate(predicates, criteriaQuery);
            });
            return ts ;
        }catch (Exception e){
            e.printStackTrace();
            throw  new SmartCommunityException(ResultMsg.IDS_IS_NOT_ILLEGAL) ;
        }
    }
    @Override
    public ApiResult findByIdWithApiResult(Long id) {
        Optional<T> t = r.findById(id);
        if (t.isPresent()){
            return  new ApiResult(SUCCESS,t.get()) ;
        }else{
            return  new ApiResult(ENTITY_ID_NOT_EXISTS,null);
        }
    }
    @Override
    public ApiResult deleteDatas(List<T> datas) {
         r.deleteInBatch(datas);
         return  new ApiResult(SUCCESS,null) ;
    }

}
