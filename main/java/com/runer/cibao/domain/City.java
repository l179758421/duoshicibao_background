package com.runer.cibao.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.runer.cibao.base.BaseBean;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/5
 *市
 * */

@Entity
@Table(name = "City")
public class City  extends BaseBean {


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "cityEntity")
    List<Area> areaList =new ArrayList<>() ;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="province_id")
    Province provinceEntity ;

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
    Long id ;

    String name ;
    String province ;


    public Province getProvinceEntity() {
        return provinceEntity;
    }

    public void setProvinceEntity(Province provinceEntity) {
        this.provinceEntity = provinceEntity;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }
}
