package com.cairnindia.csr.builder;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.cairnindia.csr.database.PostgreSQLConnection;
import com.cairnindia.csr.model.DayProduction;
import com.cairnindia.csr.model.WaterSupplier;

public class ProductionBuilder {


	public static WaterSupplier getTotalProduction(Date start_date,Date end_date,Long id) throws SQLException{
		WaterSupplier supplier = new WaterSupplier();
		ArrayList<DayProduction> production = new ArrayList<DayProduction>();
		
		
		Connection con = PostgreSQLConnection.getConnection();
		
		PreparedStatement ps =con.prepareStatement("Select * from \"getTotalProduction\"(?,?,?);");
		ps.setLong(1,id);
		ps.setDate(2, new java.sql.Date(start_date.getTime()));
		ps.setDate(3,new java.sql.Date(end_date.getTime()));
		
		ResultSet rs = ps.executeQuery();
		
		
		
		
		
		return supplier;
	}
	
	
}
