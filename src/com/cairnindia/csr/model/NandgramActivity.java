package com.cairnindia.csr.model;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "nandgram")
public class NandgramActivity {
	private long post_id,head_count,nandgram_id,address_id;
	private User author;	
	private String text;
	private String nandgram_name;
	private Long time;
	private ArrayList<Image> images;
	private String activity;
	
	
	public long getNandgramId() {
		return nandgram_id;
	}
	@XmlElement
	public void setNandgramId(long nandgram_id) {
		this.nandgram_id = nandgram_id;
	}
	
	public String getNandgramName(){
		return nandgram_name;
	}
	
	@XmlElement
	public void setNandgramName(String name){
		this.nandgram_name =name;
	}

	public long getAddressId() {
		return address_id;
	}
	@XmlElement
	public void setAddressId(long address_id) {
		this.address_id = address_id;
	}
	public long getPost_id() {
		return post_id;
	}
	@XmlElement
	public void setPost_id(long post_id) {
		this.post_id = post_id;
	}
	public long getHeadCount() {
		return head_count;
	}
	@XmlElement
	public void setHeadCount(long head_count) {
		this.head_count = head_count;
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
	public void setTime(Long time) {
		this.time = time;
	}
	public void setTime(Date time) {
		this.time = time.getTime();
	}
	
	public ArrayList<Image> getImages() {
		return images;
	}
	@XmlElement
	public void setImages(ArrayList<Image> images) {
		this.images = images;
	}
	public String getActivity() {
		return activity;
	}
	@XmlElement
	public void setActivity(String activity) {
		this.activity = activity;
	}
}
