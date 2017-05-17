package com.cairnindia.csr.model;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "feedback")
public class Feedback {
	private Long feedback_id;
	private User user,admin;
	private Integer reaction;
	private  String comment;
	private boolean completed;
	private String text;
	private Long time;

	public Long getTime() {
		return time;
	}
	@XmlElement
	public void setTime(Long time) {
		this.time = time;
	}

	
	public boolean isCompleted() {
		return completed;
	}
	@XmlElement
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	public Integer getReaction() {
		return reaction;
	}
	@XmlElement
	public void setReaction(Integer reaction) {
		this.reaction = reaction;
	}
	public String getComment() {
		return comment;
	}
	@XmlElement
	public void setComment(String comment) {
		this.comment = comment;
	}
	public User getAdmin() {
		return admin;
	}
	@XmlElement
	public void setAdmin(User admin) {
		this.admin = admin;
	}
	public User getUser() {
		return user;
	}
	@XmlElement
	public void setUser(User user) {
		this.user = user;
	}
	public Long getFeedback_id() {
		return feedback_id;
	}
	@XmlElement
	public void setFeedback_id(Long feedback_id) {
		this.feedback_id = feedback_id;
	}
	public String getText() {
		return text;
	}
	@XmlElement
	public void setText(String text) {
		this.text = text;
	}
}
