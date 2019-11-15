package com.runer.cibao.domain;

import com.runer.cibao.base.BaseBean;

import javax.persistence.*;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/9
 **/
@Entity
@Table(name = "GoodTeaches")
public class GoodTeaches  extends BaseBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;

    String imgUrl ;

    String teacherName  ;

    String teacherDes ;

    long wathNum ;

    double score ;

    int isBought ;

    String title ;
    String url ;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherDes() {
        return teacherDes;
    }

    public void setTeacherDes(String teacherDes) {
        this.teacherDes = teacherDes;
    }

    public long getWathNum() {
        return wathNum;
    }

    public void setWathNum(long wathNum) {
        this.wathNum = wathNum;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getIsBought() {
        return isBought;
    }

    public void setIsBought(int isBought) {
        this.isBought = isBought;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
