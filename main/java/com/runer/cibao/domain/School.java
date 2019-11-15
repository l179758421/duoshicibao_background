package com.runer.cibao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
 * @Date 2018/6/5
 **/

@Entity
@Table(name = "School")
public class School extends BaseBean {


    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agents_id")
    Agents agents ;



    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "school")
    List<Teacher> teachers =new ArrayList<>() ;


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "school")
    List<AppUserBindSchool> appUserBindSchools =new ArrayList<>() ;


    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.LAZY,mappedBy = "school")
    SchoolMaster schoolMaster  ;


    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "school")
    List<ClassInSchool> classInSchools =new ArrayList<>() ;


    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "school")
    List<RedeemCode>redeemCodes =new ArrayList<>();


    @Transient
    private String mastername ;

    @Transient
    private Long masterId ;

    @Transient
    private String province;//所在省

    @Transient
    private String city;//所在市

    @Transient
    private Integer seven;//7天学习人数

    @Transient
    private Integer thirty;//30天学习人数

    @Transient
    private Integer userNumber;//学校学员数

    /**
     * 省市 名称 校长 电话 地址 学校概况
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;

    String name;

    Long areaId;

    Long provinceId;

    Long cityId;

    String shcoolDes ;

    String phone ;

    String  address ;

    String detailAddress ;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    Date createTime;

    @Column(unique = true ,nullable = false)
    String uid ;


    @Transient
    @JsonProperty(value = "LAY_CHECKED")
    boolean LAY_CHECKED ;

    public Integer getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(Integer userNumber) {
        this.userNumber = userNumber;
    }

    public Integer getSeven() {
        return seven;
    }

    public void setSeven(Integer seven) {
        this.seven = seven;
    }

    public Integer getThirty() {
        return thirty;
    }

    public void setThirty(Integer thirty) {
        this.thirty = thirty;
    }

    public SchoolMaster getSchoolMaster() {
        return schoolMaster;
    }

    public void setSchoolMaster(SchoolMaster schoolMaster) {
        this.schoolMaster = schoolMaster;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getShcoolDes() {
        return shcoolDes;
    }

    public void setShcoolDes(String shcoolDes) {
        this.shcoolDes = shcoolDes;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public List<ClassInSchool> getClassInSchools() {
        return classInSchools;
    }

    public void setClassInSchools(List<ClassInSchool> classInSchools) {
        this.classInSchools = classInSchools;
    }

    public List<RedeemCode> getRedeemCodes() {
        return redeemCodes;
    }

    public void setRedeemCodes(List<RedeemCode> redeemCodes) {
        this.redeemCodes = redeemCodes;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<AppUserBindSchool> getAppUserBindSchools() {
        return appUserBindSchools;
    }

    public void setAppUserBindSchools(List<AppUserBindSchool> appUserBindSchools) {
        this.appUserBindSchools = appUserBindSchools;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public Agents getAgents() {
        return agents;
    }

    public void setAgents(Agents agents) {
        this.agents = agents;
    }

    public boolean isLAY_CHECKED() {
        return LAY_CHECKED;
    }

    public void setLAY_CHECKED(boolean LAY_CHECKED) {
        this.LAY_CHECKED = LAY_CHECKED;
    }

    public String getMastername() {
        return mastername;
    }

    public void setMastername(String mastername) {
        this.mastername = mastername;
    }

    public Long getMasterId() {
        return masterId;
    }

    public void setMasterId(Long masterId) {
        this.masterId = masterId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
