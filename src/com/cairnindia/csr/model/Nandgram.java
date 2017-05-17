package com.cairnindia.csr.model;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "nandgram")
public class Nandgram {
private long nandgram_id;
private String nandgram_name,address;
private ArrayList<Long> users;
private double longitude,latitude;
public long getNandgram_id() {
	return nandgram_id;
}
@XmlElement
public void setNandgram_id(long nandgram_id) {
	this.nandgram_id = nandgram_id;
}
public String getNandgram_name() {
	return nandgram_name;
}
@XmlElement
public void setNandgram_name(String nandgram_name) {
	this.nandgram_name = nandgram_name;
}
public String getAddress() {
	return address;
}
@XmlElement
public void setAddress(String address) {
	this.address = address;
}
public ArrayList<Long> getUsers() {
	return users;
}
@XmlElement
public void setUsers(ArrayList<Long> users) {
	this.users = users;
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
}
