package com.runer.cibao.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/6
 **/

@Entity
@Table
public class TImeTest1 {


    @Id
    @GeneratedValue(generator = "aa")
    @GenericGenerator(name="aa",strategy = "assigned")
    Long id ;

    @Temporal(TemporalType.TIMESTAMP)
    Date createtime ;

    @Temporal(TemporalType.DATE)
    Date getCreatetime ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getGetCreatetime() {
        return getCreatetime;
    }

    public void setGetCreatetime(Date getCreatetime) {
        this.getCreatetime = getCreatetime;
    }
}
