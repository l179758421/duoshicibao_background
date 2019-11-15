package com.runer.cibao.domain;


import com.runer.cibao.Config;

import java.io.Serializable;
import java.util.Date;

public class Member  implements Serializable {
	
	private Long userID;
	private String userName ;
	private Date loginTime ;
	private String loginName ;
	private Long roleId ;

	private SchoolMaster schoolMaster;

	private Teacher teacher ;

	private Admin admin ;

	private Agents agents ;


	private User user ;


	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setAgents(Agents agents) {
		this.agents = agents;
	}

	public Agents getAgents() {
		return agents;
	}

	public boolean isTeacher(){
		return  teacher==null?false:true;
	}
	public boolean isAdmin(){
		return  admin==null?false:true ;
	}
	public boolean isSuperAdmin(){
		if (isAdmin()){
			if (admin.getIsMaster()== Config.IS_SUPER_MASTER){
				return  true ;
			}
		}
		return  false ;
	}

	public boolean isSchoolMaster(){
		return  schoolMaster==null?false:true ;
	}


	public boolean isAgents(){
		return  agents ==null ?false :true ;
	}



	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public SchoolMaster getSchoolMaster() {
		return schoolMaster;
	}

	public void setSchoolMaster(SchoolMaster schoolMaster) {
		this.schoolMaster = schoolMaster;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
}