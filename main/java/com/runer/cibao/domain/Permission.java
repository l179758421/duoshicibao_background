package com.runer.cibao.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.runer.cibao.base.BaseBean;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/1
 **/
@Entity
@Table(name = "permission")
public class Permission  extends BaseBean {

    @Transient
    List<Roles>  roles =new ArrayList<>() ;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;

    String permissonName;

    //菜单相关；
    @Transient
    List<Permission> powerList =new ArrayList<>();

    String powerName;

    Long parentId ;

    //权限对应的路径；
    String powerUrl;

    String powerMenuName;

    String icon ;

    String dataPath ;

    int menuId ;

    int hasChild ;


    @Transient
    @JsonProperty(value = "LAY_CHECKED")
    boolean LAY_CHECKED ;

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermissonName() {
        return permissonName;
    }
    public void setPermissonName(String permissonName) {
        this.permissonName = permissonName;
    }


    public List<Permission> getPowerList() {
        return powerList;
    }

    public void setPowerList(List<Permission> powerList) {
        this.powerList = powerList;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getHasChild() {
        return hasChild;
    }

    public void setHasChild(int hasChild) {
        this.hasChild = hasChild;
    }

    public boolean isLAY_CHECKED() {
        return LAY_CHECKED;
    }

    public void setLAY_CHECKED(boolean LAY_CHECKED) {
        this.LAY_CHECKED = LAY_CHECKED;
    }
}
