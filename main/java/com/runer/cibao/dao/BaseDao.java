package com.runer.cibao.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/4
 **/
public class BaseDao<T extends JpaRepository>  {

    @Autowired
    public T t ;

    public T getT() {
        return t;
    }






}
