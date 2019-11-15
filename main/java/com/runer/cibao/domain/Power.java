package com.runer.cibao.domain;

import com.runer.cibao.base.BaseBean;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/4
 * 用户的权限；
 **/

@Entity
@Table(name = "Power")
public class Power extends BaseBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;

    @Transient
    List<Power> powerList =new ArrayList<>();

    String powerName;

    Long parentId ;

    //权限对应的路径；
    String powerUrl;

    String powerMenuName;

    String icon ;

    String dataPath ;

    boolean hasChild ;

    public List<Power> getPowerList() {
        if (powerList==null){
            return  new ArrayList<>() ;
        }
        return powerList;
    }
    public void setPowerList(List<Power> powerList) {
        if (powerList==null){
            powerList=new ArrayList<>() ;
        }
        this.powerList = powerList;

    }

    public boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getPowerUrl() {
        return powerUrl;
    }

    public void setPowerUrl(String powerUrl) {
        this.powerUrl = powerUrl;
    }

    public String getPowerMenuName() {
        return powerMenuName;
    }

    public void setPowerMenuName(String powerMenuName) {
        this.powerMenuName = powerMenuName;
    }

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }




}
