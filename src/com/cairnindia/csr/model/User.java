package com.cairnindia.csr.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "user")
public class User {
private long user_id;
private String name,email,hash,salt,phone;
private ArrayList<Team> user_teams;
private int account_level;
private String otp,firebase_token;
private boolean verified;
private ArrayList<Long> modules;

public ArrayList<Team> getUser_teams() {
	return user_teams;
}
@XmlElement
public void setUser_teams(ArrayList<Team> user_teams) {
	this.user_teams = user_teams;
}

public int getAccount_level() {
	return account_level;
}
@XmlElement
public void setAccount_level(int account_level) {
	this.account_level = account_level;
}

public String getOtp() {
	return otp;
}
@XmlElement
public void setOtp(String otp) {
	this.otp = otp;
}

public String getFirebase_token() {
	return firebase_token;
}
@XmlElement
public void setFirebase_token(String firebase_token) {
	this.firebase_token = firebase_token;
}

public boolean isVerified() {
	return verified;
}
@XmlElement
public void setVerified(boolean verified) {
	this.verified = verified;
}


public long getUser_id() {
	return user_id;
}
@XmlElement
public void setUser_id(long user_id) {
	this.user_id = user_id;
}
public String getName() {
	return name;
}
@XmlElement
public void setName(String name) {
	this.name = name;
}
public String getEmail() {
	return email;
}
@XmlElement
public void setEmail(String email) {
	this.email = email;
}
public String getHash() {
	return hash;
}
public void setHash(String hash) {
	this.hash = hash;
}
public String getSalt() {
	return salt;
}
public void setSalt(String salt) {
	this.salt = salt;
}
public String getPhone() {
	return phone;
}
@XmlElement
public void setPhone(String phone) {
	this.phone = phone;
}
public ArrayList<Long> getModules() {
	return modules;
}
@XmlElement
public void setModules(ArrayList<Long> modules) {
	this.modules = modules;
}
}
