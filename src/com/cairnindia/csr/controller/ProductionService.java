package com.cairnindia.csr.controller;

import java.sql.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.cairnindia.csr.model.WaterSupplier;

@Path("/ProductionService")
public class ProductionService {

	
	@GET
	@Path("/getProductionTotal/{id}/{start_date}/{end_date}")
	@Produces(MediaType.APPLICATION_JSON)
	
	public void getTotalProduction(@PathParam("id")Long id,@PathParam("start_date")Date start_date,@PathParam("end_date")Date end_date){
				
			
		
	}
	
	
	@POST
	@Path("/addProduction")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	
	
	public void addProduction(@FormParam("id")Long id,@FormParam("date")Date date,@FormParam("chiller")Long chiller,@FormParam("normal")Long normal){
		
		
		
	}
	
	
	
	
}
