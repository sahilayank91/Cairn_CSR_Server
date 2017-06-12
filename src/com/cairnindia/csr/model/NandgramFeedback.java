package com.cairnindia.csr.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name ="nandgramfeedback")
public class NandgramFeedback {

	private Long nandgram_id;
	private Double health_rating,social_rating,education_rating,nutrition_rating,womenskill_rating;
	public Double average;
	
	
	public Long getNandgram_id(){
		return nandgram_id;
	}
	
	@XmlElement
	public void setNandgram_id(Long id){
		this.nandgram_id = id;
	}
	
	public double getHealth_rating(){
		return health_rating.intValue();
	}
	
	@XmlElement
	public void setHealth_rating(double health){
		this.health_rating = health;
	}
	
	public double getSocial_rating(){
		return social_rating.intValue();
	}
	
	@XmlElement
	public void setSocial_rating(double social){
		this.social_rating = social;
	}
	

	public double getEducation_rating(){
		return education_rating.intValue();
	}
	
	@XmlElement
	public void setEducation_rating(double education){
		this.education_rating = education;
	}
	

	public double getNutrition_rating(){
		return nutrition_rating.intValue();
	}
	
	@XmlElement
	public void setNutrition_rating(double nutrition){
		this.nutrition_rating = nutrition;
	}
	

	public double getWomenskill_rating(){
		return womenskill_rating.intValue();
	}
	
	@XmlElement
	public void setWomenskill_rating(double women){
		this.womenskill_rating = women;
	}
	
	
	@XmlElement
	public void setAverage(Double average){
		this.average = average;
		}
	
	public Double getAverage(){
		return average;
	}
	
}
