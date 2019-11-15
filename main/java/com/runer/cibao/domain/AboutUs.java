package com.runer.cibao.domain;

import com.runer.cibao.base.BaseBean;

import javax.persistence.*;

@Entity
@Table(name = "AboutUs")
public class AboutUs extends BaseBean {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;

    String title;//标题

    @Column(length = 2000)
    String content;//内容

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
