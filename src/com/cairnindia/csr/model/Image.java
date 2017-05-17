package com.cairnindia.csr.model;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "image")
public class Image {
private String filename,caption;
private long image_id;
private ArrayList<Long> likes;
private Long time;
public String getFilename() {
	return filename;
}
@XmlElement
public void setFilename(String filename) {
	this.filename = filename;
}
public String getCaption() {
	return caption;
}
@XmlElement
public void setCaption(String caption) {
	this.caption = caption;
}
public long getImage_id() {
	return image_id;
}
@XmlElement
public void setImage_id(long image_id) {
	this.image_id = image_id;
}
public ArrayList<Long> getLikes() {
	return likes;
}
@XmlElement
public void setLikes(ArrayList<Long> likes) {
	this.likes = likes;
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

}
