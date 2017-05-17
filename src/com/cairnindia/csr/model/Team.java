package com.cairnindia.csr.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "team")
public class Team {
	private long team_id;
	private String name,description;
	private ArrayList<User> members,admins;
	private ArrayList<Message> messages;

	public long getTeam_id() {
		return team_id;
	}
	@XmlElement
	public void setTeam_id(long team_id) {
		this.team_id = team_id;
	}
	public String getName() {
		return name;
	}
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	@XmlElement
	public void setDescription(String description) {
		this.description = description;
	}
	public ArrayList<User> getMembers() {
		return members;
	}
	@XmlElement
	public void setMembers(ArrayList<User> members) {
		this.members = members;
	}
	public ArrayList<Message> getMessages() {
		return messages;
	}
	@XmlElement
	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}
	public ArrayList<User> getAdmins() {
		return admins;
	}
	@XmlElement
	public void setAdmins(ArrayList<User> admins) {
		this.admins = admins;
	}
}
