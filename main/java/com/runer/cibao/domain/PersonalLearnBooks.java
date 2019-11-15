package com.runer.cibao.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.runer.cibao.base.BaseBean;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/25
 * 个人的图书列表
 **/
@Entity
@Table(name = "PersonalLearnBooks")
public class PersonalLearnBooks  extends BaseBean {

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY ,mappedBy ="personalLearnBooks" )
    List<PersonalLearnBook> personalLearnBooks ;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;


    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    AppUser appUser ;


    public List<PersonalLearnBook> getPersonalLearnBooks() {
        return personalLearnBooks;
    }

    public void setPersonalLearnBooks(List<PersonalLearnBook> personalLearnBooks) {
        this.personalLearnBooks = personalLearnBooks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
}
