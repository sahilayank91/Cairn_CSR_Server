package com.cairnindia.csr.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.cairnindia.csr.builder.ProductionBuilder;
import com.cairnindia.csr.model.DayProduction;
import com.cairnindia.csr.model.Fontus;


@Path("/ProductionService")
public class ProductionService {

	
	@GET
	@Path("/getProductionTotal/{id}/{start_date}/{end_date}")
	@Produces(MediaType.APPLICATION_JSON)
	
	public ArrayList<DayProduction> getTotalProduction(@PathParam("id")Long id,@PathParam("start_date")Date start_date,@PathParam("end_date")Date end_date) throws SQLException{
			ArrayList<DayProduction> list = new ArrayList<DayProduction>();
			list = ProductionBuilder.getTotalProduction(start_date, end_date, id);
			return list;
	}
	
	
	@POST
	@Path("/addProduction")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	
	public Long addProduction(@FormParam("id")Long id,@FormParam("date")Date date,@FormParam("chiller")Long chiller,@FormParam("normal")Long normal) throws SQLException{
		Long supplier_id = ProductionBuilder.addProduction(chiller, normal, id);	
		return supplier_id;
	}
	
	@GET
	@Path("/getList/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Fontus> getList(@PathParam("id")Long id) throws SQLException{
		ArrayList<Fontus> list = new ArrayList<Fontus>();
		list = ProductionBuilder.getList(id);
		return list;	
	}
	
	
	
}
