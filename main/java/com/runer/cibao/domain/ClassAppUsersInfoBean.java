package com.runer.cibao.domain;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/7
 **/
public class ClassAppUsersInfoBean  {

    long  usersCount ;

    long classId ;

    String  className ;

    long  schoolId ;

    String  schoolName ;

    long  bookCreateCount ;


    public long  getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(Long usersCount) {
        this.usersCount = usersCount;
    }

    public long  getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public long  getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(long  schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public long  getBookCreateCount() {
        return bookCreateCount;
    }

    public void setBookCreateCount(long  bookCreateCount) {
        this.bookCreateCount = bookCreateCount;
    }
}
