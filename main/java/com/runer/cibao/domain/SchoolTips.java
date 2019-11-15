package com.runer.cibao.domain;

import com.runer.cibao.base.BaseBean;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
@Table(name = "SchoolTips")
public class SchoolTips extends BaseBean {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;

    /**
     * 名言警句
     */
    String famous;

    /**
     * 翻译
     */
    String translate;

    /**
     * 作者
     */
    String author;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="school_id")
    School school;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFamous() {
        return famous;
    }

    public void setFamous(String famous) {
        this.famous = famous;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
