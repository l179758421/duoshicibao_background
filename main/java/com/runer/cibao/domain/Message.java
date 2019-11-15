package com.runer.cibao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.runer.cibao.Config;
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
 * @Date 2018/7/5
 **/



@Entity
@Table(name = "Message")
public class Message extends BaseBean {

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "message")
    List<MessageRead> messageReadList =new ArrayList<>() ;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;

    @Column(length = 1000)
    String msgContent ;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_school_id")
    ClassInSchool classInSchool ;

    Integer msgType = Config.SYSTEM_MSG ;
    /**
     * 关联的具体业务的id ；
     */
    Long relatedId ;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "send_user_id")
    User sendUser ;


    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    Date createDate ;

    int pushState= Config.PUSH_FAILED ;

    String url ;

    String title ;


    public Message() {
    }

    public Message(List<MessageRead> messageReadList, String msgContent, ClassInSchool classInSchool, Integer msgType, Long relatedId, User sendUser, Date createDate, int pushState) {
        this.messageReadList = messageReadList;
        this.msgContent = msgContent;
        this.classInSchool = classInSchool;
        this.msgType = msgType;
        this.relatedId = relatedId;
        this.sendUser = sendUser;
        this.createDate = createDate;
        this.pushState = pushState;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }


    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public Long getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Long relatedId) {
        this.relatedId = relatedId;
    }

    public User getSendUser() {
        return sendUser;
    }

    public void setSendUser(User sendUser) {
        this.sendUser = sendUser;
    }



    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getPushState() {
        return pushState;
    }

    public void setPushState(int pushState) {
        this.pushState = pushState;
    }
    public List<MessageRead> getMessageReadList() {
        return messageReadList;
    }

    public void setMessageReadList(List<MessageRead> messageReadList) {
        this.messageReadList = messageReadList;
    }

    public ClassInSchool getClassInSchool() {
        return classInSchool;
    }

    public void setClassInSchool(ClassInSchool classInSchool) {
        this.classInSchool = classInSchool;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
