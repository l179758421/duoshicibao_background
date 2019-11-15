package com.runer.cibao.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.runer.cibao.base.BaseBean;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/5
 * 区
 **/

@Entity
@Table(name="Area")
public class Area  extends BaseBean {

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    City cityEntity ;

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
    Long id ;

    @JSONField(name = "国金")
    String name ;

    String city ;

    public City getCityEntity() {
        return cityEntity;
    }

    public void setCityEntity(City cityEntity) {
        this.cityEntity = cityEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }



}
