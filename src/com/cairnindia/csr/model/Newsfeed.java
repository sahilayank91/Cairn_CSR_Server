package com.cairnindia.csr.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "newsfeed")
//may not need this at all
public class Newsfeed {
private ArrayList<Long> posts;
private long user;

public ArrayList<Long> getPosts() {
	return posts;
}
@XmlElement
public void setPosts(ArrayList<Long> posts) {
	this.posts = posts;
}
public long getUser() {
	return user;
}
@XmlElement
public void setUser(long user) {
	this.user = user;
}
}
