package com.cairnindia.csr.builder;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.cairnindia.csr.database.PostgreSQLConnection;
import com.cairnindia.csr.model.DayProduction;
import com.cairnindia.csr.model.Fontus;


public class ProductionBuilder {


	public static ArrayList<DayProduction> getTotalProduction(Date start_date,Date end_date,Long id) throws SQLException{
		
		ArrayList<DayProduction> production = new ArrayList<DayProduction>();	
		Connection con = PostgreSQLConnection.getConnection();
		PreparedStatement ps =con.prepareStatement("Select * from \"getDaysProduction\"(?,?,?);");
		ps.setLong(1,id);
		ps.setDate(2, new java.sql.Date(start_date.getTime()));
		ps.setDate(3,new java.sql.Date(end_date.getTime()));
		
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			DayProduction prod = new DayProduction();
			prod.setTime(rs.getTimestamp("day_date").getTime());
			prod.setchiller(rs.getLong("chiller"));
			prod.setNormal(rs.getLong("normal"));
			production.add(prod);	
		}
		return production;
	}
	
	public static Long addProduction(Long chiller,Long normal,Long id) throws SQLException{
		
		Connection con = PostgreSQLConnection.getConnection();
		PreparedStatement ps =con.prepareStatement("Select * from \"addDayProduction\"(?,?,?);");
		ps.setLong(1,id);	
		ps.setLong(2, chiller);
		ps.setLong(3, normal);
		ResultSet rs = ps.executeQuery();	
		rs.next();
		long supplier_id = rs.getLong("supplierId");
		return supplier_id;
		
	}
	public static ArrayList<Fontus> getList(Long id) throws SQLException{
		
		ArrayList<Fontus> list = new ArrayList<Fontus>();
		Connection con = PostgreSQLConnection.getConnection();
		PreparedStatement  ps= con.prepareStatement("Select * from \"getWaterDepartmentList\"(?);");
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()){
			Fontus  fontus = new Fontus();
			fontus.setArea_name(rs.getString("name"));
			list.add(fontus);
		}
		
		return list;	
	}
	
}




