package com.cairnindia.csr.builder;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.joda.time.Days;

import com.cairnindia.csr.database.PostgreSQLConnection;

import com.cairnindia.csr.model.Nandgram;
import com.cairnindia.csr.model.NandgramAttendance;


public class NandgramBuilder {
	
	public static ArrayList<Nandgram>getNandgramList(){
		Connection con;
		ArrayList<Nandgram>nandgrams=new ArrayList<Nandgram>();
		try {
			con = PostgreSQLConnection.getConnection();
			PreparedStatement proc=con.prepareStatement("Select * from public.\"getNandgramList\"();");
		
System.out.println(proc);
			ResultSet rs = proc.executeQuery(); /*throwing exception*/
			while( rs.next()){
				Nandgram current=new Nandgram();
				//nandgram_id, name, address, longitude, latitude, "user"
				current.setNandgram_id(rs.getLong("nandgram_id"));
				current.setNandgram_name(rs.getString("name"));
				current.setAddress(rs.getString("address"));
				current.setLongitude(rs.getDouble("longitude"));
				current.setLatitude(rs.getDouble("latitude"));
				Array users = rs.getArray("users");
				ArrayList<Long>user_ids=new ArrayList<>();
				ResultSet rs2 = users.getResultSet();
				while(rs2.next()){
					user_ids.add(rs2.getLong(1));
				}
				current.setUsers(user_ids);
				nandgrams.add(current);
			}
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nandgrams;
		
	}
	
	public static Long addNandgramAttendance(NandgramAttendance attendance){
		Connection con;
		try {
			con = PostgreSQLConnection.getConnection();
			PreparedStatement proc=con.prepareStatement("Select * from public.\"addNandgramAttendance\"(?,?,?,?,?,?,?);");
			proc.setDouble(1, attendance.getLongitude());
			proc.setDouble(2,attendance.getLatitude());
			proc.setInt(3,attendance.getHead_count());
			proc.setLong(4,attendance.getNandgram_id());
			proc.setLong(5,attendance.getUser_id());
			proc.setLong(6,attendance.getImage_id());
			proc.setInt(7, attendance.getSlot());
System.out.println(proc);
			ResultSet rs = proc.executeQuery(); /*throwing exception*/
			if( rs.next()){
				return (rs.getLong(1));
			}
			else return null;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void updateNandgramAttendance(NandgramAttendance attendance){
		Connection con;
		try {
			con = PostgreSQLConnection.getConnection();
			PreparedStatement proc=con.prepareStatement("Select * from public.\"updateNandgramAttendance\"(?,?,?,?,?,?);");
			proc.setDouble(1, attendance.getLongitude());
			proc.setDouble(2,attendance.getLatitude());
			proc.setInt(3,attendance.getHead_count());
			proc.setLong(4,attendance.getUser_id());
			proc.setLong(5,attendance.getImage_id());
			proc.setLong(6,attendance.getAttendance_id());
System.out.println(proc);
			ResultSet rs = proc.executeQuery(); /*throwing exception*/

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ArrayList<NandgramAttendance> getAttendance(Date date){
		ArrayList<NandgramAttendance> day_attendance=new ArrayList<>();
		try {
			Connection con=PostgreSQLConnection.getConnection();
			PreparedStatement proc=con.prepareStatement("Select * from \"getAttendance\"(?);");
			proc.setDate(1, new java.sql.Date(date.getTime()));
			ResultSet rs=proc.executeQuery();
			while(rs.next()){
				NandgramAttendance current=new NandgramAttendance();
				
				current.setHead_count(rs.getInt("head_count"));
				current.setTime(rs.getTimestamp("timestamp").getTime());
				current.setSlot(rs.getInt("slot"));
				System.out.println(current.getHead_count()+" "+current.getDate());
				day_attendance.add(current);
				
			}
			
			return day_attendance;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return day_attendance;
	}

	public static ArrayList<NandgramAttendance> getRangeAttendance(Date start_date,Date end_date){
		ArrayList<NandgramAttendance> days_attendance=new ArrayList<NandgramAttendance>();
		try {
			Connection con=PostgreSQLConnection.getConnection();
			PreparedStatement proc=con.prepareStatement("Select * from public.\"getDaysAttendance\"(?,?);");
			proc.setTimestamp(1, new Timestamp(start_date.getTime()));
			proc.setTimestamp(2, new Timestamp(end_date.getTime()));
		
			ResultSet rs=proc.executeQuery();
			
			System.out.println(proc);
			
			while(rs.next()){
				NandgramAttendance current=new NandgramAttendance();
				current.setHead_count(rs.getInt("head_count"));
				current.setTime(rs.getTimestamp("day_date").getTime());
				current.setSlot(rs.getInt("slot"));
				days_attendance.add(current);
			}
			
			return days_attendance;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return days_attendance;
	}

	
	public static ArrayList<NandgramAttendance> getWeekAttendance(Date month){
		ArrayList<NandgramAttendance> days_attendance=new ArrayList<NandgramAttendance>();
		try {
			Calendar start=Calendar.getInstance();
			start.setTime(month);
			start.set(Calendar.DAY_OF_MONTH,1);
			start.set(Calendar.HOUR_OF_DAY,0);
			start.set(Calendar.MINUTE,0);
			start.set(Calendar.SECOND,0);
			start.set(Calendar.MILLISECOND,0);
			Calendar end=Calendar.getInstance();
			end.setTime(start.getTime());
			end.set(Calendar.DAY_OF_MONTH,end.getActualMaximum(Calendar.DAY_OF_MONTH));
	 days_attendance=getRangeAttendance(start.getTime(),end.getTime());
		
			//if(1==1)return null;
			Iterator<NandgramAttendance> iterator=days_attendance.iterator();
			
			Calendar current=Calendar.getInstance();
			current.setTime(start.getTime());
			Long week=0l;
			while(iterator.hasNext()){
				NandgramAttendance current_day=iterator.next();
				System.out.println(current_day.getDate()+"  "+current_day.getHead_count());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return days_attendance;
	}
	
	public static void main(String[] args) {
	/*NandgramAttendance attendance=new NandgramAttendance();
	attendance.setLongitude(23.34);attendance.setLatitude(34.23);
	attendance.setHead_count(23);attendance.setImage_id(7);
	attendance.setAttendance_id(6);attendance.setUser_id(4);
	updateNandgramAttendance(attendance);*/
		
		Calendar start=Calendar.getInstance();
		//start.setTime(month);
		start.set(Calendar.MONTH,3);
		start.set(Calendar.YEAR,2017);
		start.set(Calendar.DAY_OF_MONTH,18);
		start.set(Calendar.HOUR_OF_DAY,0);
		start.set(Calendar.MINUTE,0);
		start.set(Calendar.SECOND,0);
		start.set(Calendar.MILLISECOND,0);
		//Calendar end=Calendar.getInstance();
		//end.setTime(start.getTime());
		//end.set(Calendar.DAY_OF_MONTH,end.getActualMaximum(Calendar.DAY_OF_MONTH));
		//ArrayList<Long> all_smiles=getRangeSmiles(start.getTime(),end.getTime(),userid);
		ArrayList<NandgramAttendance> day_attendance = getAttendance(start.getTime());
//		ArrayList<Attendance> current=getWeekAttendance(new Date());
}
}
