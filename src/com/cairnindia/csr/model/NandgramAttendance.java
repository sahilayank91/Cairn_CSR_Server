package com.cairnindia.csr.model;

import java.util.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "nandgram_attendance")
public class NandgramAttendance {
private long attendance_id,nandgram_id,user_id,image_id;
private double longitude,latitude;
private Long time;
private int head_count;
private int slot;

public long getAttendance_id() {
	return attendance_id;
}
@XmlElement
public void setAttendance_id(long attendance_id) {
	this.attendance_id = attendance_id;
}
public long getNandgram_id() {
	return nandgram_id;
}
@XmlElement
public void setNandgram_id(long nandgram_id) {
	this.nandgram_id = nandgram_id;
}
public long getUser_id() {
	return user_id;
}
@XmlElement
public void setUser_id(long user_id) {
	this.user_id = user_id;
}
public long getImage_id() {
	return image_id;
}
@XmlElement
public void setImage_id(long image_id) {
	this.image_id = image_id;
}
public double getLongitude() {
	return longitude;
}
@XmlElement
public void setLongitude(double longitude) {
	this.longitude = longitude;
}
public double getLatitude() {
	return latitude;
}
@XmlElement
public void setLatitude(double latitude) {
	this.latitude = latitude;
}
public int getHead_count() {
	return head_count;
}
@XmlElement
public void setHead_count(int head_count) {
	this.head_count = head_count;
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
public int getSlot() {
	return slot;
}
@XmlElement
public void setSlot(int slot) {
	this.slot = slot;
}

}
