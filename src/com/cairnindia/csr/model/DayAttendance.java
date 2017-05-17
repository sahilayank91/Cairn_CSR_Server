package com.cairnindia.csr.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "attendance")
public class DayAttendance{
	private int head_count_slot1;
	private int head_count_slot2;
	private Long time;
	
	
	
	

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
	
}
