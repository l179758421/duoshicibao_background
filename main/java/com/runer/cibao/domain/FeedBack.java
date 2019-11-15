package com.runer.cibao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.runer.cibao.base.BaseBean;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/12
 **/

@Entity
@Table(name = "feedBack")
public class FeedBack extends BaseBean {


    /**
     * 提问者姓名 提问时间 提问内容 回复人 回复时间 回复内容
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;

    /**
     * 是否解决
     * 0 未解决，1已解决
     */
    Integer ifSolve=0;

    @Column(length = 500)
    String content ;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "askUserId")
    AppUser askUser ;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    Date askTime ;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    Date answerTiem ;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answerUserId")
    User answerUser;

    @Column(length = 500)
    String answerContent ;

    /**
     * 可以存储的图片；
     */
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "feedBack")
    List<ImageInDbForCache> imageInDbForCacheList =new ArrayList<>() ;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public AppUser getAskUser() {
        return askUser;
    }

    public void setAskUser(AppUser askUser) {
        this.askUser = askUser;
    }

    public User getAnswerUser() {
        return answerUser;
    }

    public void setAnswerUser(User answerUser) {
        this.answerUser = answerUser;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public List<ImageInDbForCache> getImageInDbForCacheList() {
        return imageInDbForCacheList;
    }

    public void setImageInDbForCacheList(List<ImageInDbForCache> imageInDbForCacheList) {
        this.imageInDbForCacheList = imageInDbForCacheList;
    }

    public Date getAskTime() {
        return askTime;
    }

    public void setAskTime(Date askTime) {
        this.askTime = askTime;
    }

    public Date getAnswerTiem() {
        return answerTiem;
    }

    public void setAnswerTiem(Date answerTiem) {
        this.answerTiem = answerTiem;
    }

    public Integer getIfSolve() {
        return ifSolve;
    }

    public void setIfSolve(Integer ifSolve) {
        this.ifSolve = ifSolve;
    }
}
