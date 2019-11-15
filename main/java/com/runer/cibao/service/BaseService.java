package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.exception.SmartCommunityException;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author szhua
 * 2018-04-19
 * 9:19
 * @Descriptionsbaby_photos== BaseService
 * <p>
 * T:实体类class
 * R： extends JpaRepository ;
 * baseService 提供通用service ;
 **/
public interface BaseService<T, R extends JpaRepository<T, Long>> {

    T findById(Long id) throws SmartCommunityException;

    boolean deleteById(Long id) throws SmartCommunityException;

    void deleteByIds(String ids) throws SmartCommunityException;

    List<T> findAll();

    Page<T> findByPage(Integer page, Integer rows) ;

    T saveOrUpdate(T t) throws SmartCommunityException;

    List<T> saveOrUpdate(List<T> datas) throws SmartCommunityException;

    T save(T t) throws SmartCommunityException;

    T update(T t) throws SmartCommunityException;


    List<T> findByIds(String ids) throws SmartCommunityException;


    ApiResult findByIdWithApiResult(Long id) ;

    ApiResult deleteDatas(List<T> datas) ;











}
