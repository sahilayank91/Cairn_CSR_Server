package com.cairnindia.csr.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name = "message")
public class Message {
private Long message_id;
private User author;
private String text;
private Long time;

public Long getMessage_id() {
	return message_id;
}
@XmlElement
public void setMessage_id(Long message_id) {
	this.message_id = message_id;
}
public User getAuthor() {
	return author;
}
@XmlElement
public void setAuthor(User author) {
	this.author = author;
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
public void setTime(Date time) {
	this.time = time.getTime();
}
}
