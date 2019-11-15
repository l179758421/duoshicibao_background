package com.runer.cibao.domain;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.runer.cibao.base.BaseBean;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/4
 **/


@Entity
@Table(name = "Roles")
public class Roles extends BaseBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;


    @Column(unique = true)
    String roleName ;

    @Column(length = 300)
    String permissionIds ;

    @Transient
    List<Permission> permissions =new ArrayList<>();

    @Transient
    @JsonIgnore
    List<User> users =new ArrayList<>() ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }


    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public String getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(String permissionIds) {
        this.permissionIds = permissionIds;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
