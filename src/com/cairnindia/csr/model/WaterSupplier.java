package com.cairnindia.csr.model;

import java.sql.Date;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

public class WaterSupplier {

	private Long supplier_id;
	private Long chiller,normal;
	private Date launch_date;
	private ArrayList<Long> production;
	private Long grandTotal;
	private Double latitude,longitude;
	
	
	@XmlElement
	public void setSupplier_id(Long id){
		this.supplier_id = id;
	}
	public Long getSupplier_id(){
		return supplier_id;
	}
	
	@XmlElement
	public void setLongitude(double longitude){
		this.longitude =longitude;
	}
	
	public Double getLongitude(){
		return longitude;
	}
	
	@XmlElement
	public void setLatitude(double latitude){
		this.latitude =longitude;
	}
	
	public Double getLatitude(){
		return latitude;
	}
	
	@XmlElement
	public void setChiller(Long chiller){
		this.chiller = chiller;
	}
	
	public Long getChiller(){
		return chiller;
	}
	
	@XmlElement
	public void setNormal(Long normal){
		this.normal = normal;
	}
	
	public Long getNormal(){
		return normal;
	}
	
	public Long getGrandTotal(){
		grandTotal = chiller+normal;
		return grandTotal;
	}
	
	@XmlElement
	public void setlaunchDate(Date date){
		this.launch_date = date;
	}
	
	public Date getLaunchDate(){
		return launch_date;
	}
	
	
	
	
	
	
}
