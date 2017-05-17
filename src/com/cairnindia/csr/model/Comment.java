package com.cairnindia.csr.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Shubhi
 *
 */
@XmlRootElement(name = "comment")
public class Comment {
private long comment_id;
private User author;
private String text;
private Long time;
public long getComment_id() {
	return comment_id;
}
@XmlElement
public void setComment_id(long comment_id) {
	this.comment_id = comment_id;
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
public Long getTime() {
	return time;
}
public Date getDate() {
	return new Date(time);
}
@XmlElement
public void setTime(Long time) {
	this.time = time;
}
public void setTime(Date time) {
	this.time = time.getTime();
}

}
