package com.cairnindia.csr.model;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "post")
public class Post {
private long post_id,smiles;
private User author;
private ArrayList<Comment> comments;
private ArrayList<User> likers;
private ArrayList<Image> images;
private String text;
private Long time;
private long likes_count,comments_count;

public long getPost_id() {
	return post_id;
}
@XmlElement
public void setPost_id(long post_id) {
	this.post_id = post_id;
}
public long getSmiles() {
	return smiles;
}
@XmlElement
public void setSmiles(long smiles) {
	this.smiles = smiles;
}
public User getAuthor() {
	return author;
}
@XmlElement
public void setAuthor(User author) {
	this.author = author;
}
public ArrayList<Image> getImages() {
	return images;
}
@XmlElement
public void setImages(ArrayList<Image> images) {
	this.images = images;
}
public ArrayList<Comment> getComments() {
	return comments;
}
@XmlElement
public void setComments(ArrayList<Comment> comments) {
	this.comments = comments;
}
public ArrayList<User> getLikers() {
	return likers;
}
@XmlElement
public void setLikers(ArrayList<User> likers) {
	this.likers = likers;
}
public String getText() {
	return text;
}
@XmlElement
public void setText(String text) {
	this.text = text;
}
public Date getDate() {
	return new Date(time);
}
public Long getTime() {
	return time;
}
@XmlElement
public void setTime(Long time) {
	this.time = time;
}
public void setTime(Date time) {
	this.time = time.getTime();
}
public long getComments_count() {
	return comments_count;
}
@XmlElement
public void setComments_count(long comments_count) {
	this.comments_count = comments_count;
}
public long getLikes_count() {
	return likes_count;
}
@XmlElement
public void setLikes_count(long likes_count) {
	this.likes_count = likes_count;
}
}
