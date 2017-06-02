package com.cairnindia.csr.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "attendance")
public class DayAttendance{
	private int head_count_slot1;
	private int head_count_slot2;
	private Long time;
	private String slot1_image;
	private String slot2_image;
	
	
	
	

	public Long getTime() {
		return time;
	}
	@XmlElement
	public void setTime(Long time) {
		
		this.time = time;
	}
	public int getHead_count_slot1() {
		return head_count_slot1;
	}
	@XmlElement
	public void setHead_count_slot1(int head_count_slot1) {
		this.head_count_slot1 = head_count_slot1;
	}
	public int getHead_count_slot2() {
		return head_count_slot2;
	}
	@XmlElement
	public void setHead_count_slot2(int head_count_slot2) {
		this.head_count_slot2 = head_count_slot2;
	}
	
	
	@XmlElement
	public void setSlot1_image(String image){
		this.slot1_image = image;
	}
	public String getSlot1_image(){
		return slot1_image;
	}
	
	@XmlElement
	public void setSlot2_image(String image){
		this.slot2_image = image;
	}
	
	public String getSlot2_image(){
		return slot2_image;
	}
	
}
