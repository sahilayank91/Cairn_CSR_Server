package com.cairnindia.csr.model;


import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "nandgram")
public class NandgramLocations {
private long address_id;
private String address;
private long nandghar_count;


public long getAddress_id() {
	return address_id;
}
@XmlElement
public void setAddress_id(long address_id) {
	this.address_id = address_id;
}

public String getAddress() {
	return address;
}
@XmlElement
public void setAddress(String address) {
	this.address = address;
}


@XmlElement 
public void setNandghar_Count(long nandgram_count){
	this.nandghar_count = nandgram_count;
}

public long getNandghar_Count(){
	return nandghar_count;
}
}