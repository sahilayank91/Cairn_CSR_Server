package com.cairnindia.csr.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "statistics")
public class Statistics {
	private Long object_id;
	private boolean team;
	private ArrayList<Long>week,month,year;
	private ArrayList<Long>week_dates,month_dates,year_dates;
	public Long getObject_id() {
		return object_id;
	}
	@XmlElement
	public void setObject_id(Long object_id) {
		this.object_id = object_id;
	}
	
	public ArrayList<Long> getWeek() {
		return week;
	}
	@XmlElement
	public void setWeek(ArrayList<Long> week) {
		this.week = week;
	}
	public ArrayList<Long> getMonth() {
		return month;
	}
	@XmlElement
	public void setMonth(ArrayList<Long> month) {
		this.month = month;
	}
	public ArrayList<Long> getYear() {
		return year;
	}
	@XmlElement
	public void setYear(ArrayList<Long> year) {
		this.year = year;
	}
	public ArrayList<Long> getWeek_dates() {
		return week_dates;
	}
	@XmlElement
	public void setWeek_dates(ArrayList<Long> week_dates) {
		this.week_dates = week_dates;
	}
	public ArrayList<Long> getMonth_dates() {
		return month_dates;
	}
	@XmlElement
	public void setMonth_dates(ArrayList<Long> month_dates) {
		this.month_dates = month_dates;
	}
	public ArrayList<Long> getYear_dates() {
		return year_dates;
	}
	@XmlElement
	public void setYear_dates(ArrayList<Long> year_dates) {
		this.year_dates = year_dates;
	}
	public boolean isTeam() {
		return team;
	}
	@XmlElement
	public void setTeam(boolean team) {
		this.team = team;
	}

	

}
