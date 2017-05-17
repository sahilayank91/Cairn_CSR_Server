package com.cairnindia.csr.model;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "userProfile")
public class UserProfile {
private Long user_id;
private String user_name;
private String summary, department;
private Long member_since;
public Long getUser_id() {
	return user_id;
}
@XmlElement
public void setUser_id(Long user_id) {
	this.user_id = user_id;
}
public String getSummary() {
	return summary;
}
@XmlElement
public void setSummary(String summary) {
	this.summary = summary;
}
public String getDepartment() {
	return department;
}
@XmlElement
public void setDepartment(String department) {
	this.department = department;
}
public Date getMember_sinceDate() {
	return new Date(member_since);
}
public Long getMember_sinceTime(){
	return member_since;
}
@XmlElement
public void setMember_sinceTime(Long member_since) {
	this.member_since = member_since;
}
@XmlElement
public void setMember_since(Date member_since) {
	this.member_since = member_since.getTime();
}
@XmlElement
public String getUser_name() {
	return user_name;
}
public void setUser_name(String user_name) {
	this.user_name = user_name;
}
}
