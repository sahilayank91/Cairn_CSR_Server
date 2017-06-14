package com.cairnindia.csr.model;

import javax.xml.bind.annotation.XmlElement;

public class DayProduction {

	private Long chiller,normal;
	private Long grandTotal;
	private Long id,time;
	private Long chiller_can_sell;
	

	public Long getTime() {
		return time;
	}
	@XmlElement
	public void setTime(Long time) {
		
		this.time = time;
	}
	
	@XmlElement
	public void setchiller(Long chiller){
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
	public void setChillerSell(Long chiller){
		this.chiller_can_sell = chiller;
	}
	
	public Long getChillerSell(Long chiller){
		return chiller_can_sell;
	}
	
	
}
