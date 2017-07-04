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



import com.cairnindia.csr.database.PostgreSQLConnection;
import com.cairnindia.csr.model.DayAttendance;
import com.cairnindia.csr.model.Image;
import com.cairnindia.csr.model.Nandgram;
import com.cairnindia.csr.model.NandgramActivity;
import com.cairnindia.csr.model.NandgramAttendance;
import com.cairnindia.csr.model.NandgramLocations;
import com.cairnindia.csr.model.Post;
import com.cairnindia.csr.model.User;


public class NandgramBuilder {
	
	public static ArrayList<Nandgram>getNandgramList(Long address_id){
		Connection con;
		ArrayList<Nandgram>nandgrams=new ArrayList<Nandgram>();
		try {
			con = PostgreSQLConnection.getConnection();
			PreparedStatement proc=con.prepareStatement("Select * from public.\"getNandgramList\"(?);");
			proc.setLong(1,address_id);
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
	
	
	public static ArrayList<NandgramLocations> getNandgramAddress() throws SQLException{
		Connection con;
		ArrayList<NandgramLocations> nandgrams = new ArrayList<NandgramLocations>();
		
		con = PostgreSQLConnection.getConnection();
		PreparedStatement ps = con.prepareStatement("Select * from public.\"getNandgramAddress\"();");
		System.out.println(ps);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			NandgramLocations current = new NandgramLocations();
			current.setAddress_id(rs.getLong("address_id"));
			current.setAddress(rs.getString("address"));
			current.setNandghar_Count(rs.getLong("nandghar_count"));
			nandgrams.add(current);
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
			proc.setLong(3,attendance.getHead_count());
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
	
	public static DayAttendance getNandgramAttendance(Date date,Long id) throws SQLException{
		
		Connection con;
		con = PostgreSQLConnection.getConnection();
		PreparedStatement ps = con.prepareStatement("Select * from public.\"getNandgramAttendance\"(?,?);");
		ps.setDate(1, new java.sql.Date(date.getTime()));
		ps.setLong(2, id);
		ResultSet rs = ps.executeQuery();
	
		DayAttendance dayattendance = new DayAttendance();
		while(rs.next()){
			if(rs.getLong("slot")==1){
				dayattendance.setHead_count_slot1(rs.getInt("head_count"));
				dayattendance.setSlot1_image(rs.getString("filename"));
				
			}else {
				dayattendance.setHead_count_slot2(rs.getInt("head_count"));
				dayattendance.setSlot2_image(rs.getString("filename"));
			}		
		}
	
		return dayattendance;
	
	}	
	
	
	public static NandgramAttendance getDayAttendance(Date date, int id, int slot) throws SQLException{
			NandgramAttendance current = new NandgramAttendance();
			
			Connection con = PostgreSQLConnection.getConnection();
			PreparedStatement ps = con.prepareStatement("Select  * from public.\"getNandgramAttendance\"(?,?,?);");
			ps.setDate(1,new java.sql.Date(date.getTime()));
			ps.setInt(2, id);
			ps.setInt(3, slot);
			ResultSet rs = ps.executeQuery();
			
			
			if(rs!=null){
				current.setHead_count(rs.getInt("head_count"));
				current.setImageName(rs.getString("filename"));
				return current;
			}	
		return null;
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

	public static ArrayList<NandgramAttendance> getRangeAttendance(Date start_date,Date end_date,Long id){
		ArrayList<NandgramAttendance> days_attendance=new ArrayList<NandgramAttendance>();
		try {
			Connection con=PostgreSQLConnection.getConnection();
			PreparedStatement proc=con.prepareStatement("Select * from public.\"getDaysAttendance\"(?,?,?);");
			proc.setTimestamp(1, new Timestamp(start_date.getTime()));
			proc.setTimestamp(2, new Timestamp(end_date.getTime()));
			proc.setLong(3, id);
		
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
	
	public static ArrayList<Long> getRangeHeadCount(Date start_date,Date end_date,Long id){
		ArrayList<Long> days_attendance=new ArrayList<Long>();
		try {
			Connection con=PostgreSQLConnection.getConnection();
			PreparedStatement proc=con.prepareStatement("Select * from public.\"getDaysHeadCount\"(?,?,?);");
			proc.setTimestamp(1, new Timestamp(start_date.getTime()));
			proc.setTimestamp(2, new Timestamp(end_date.getTime()));
			proc.setLong(3, id);
		
			ResultSet rs=proc.executeQuery();
			
			System.out.println(proc);
			
			while(rs.next()){
				Long headcount =(long) rs.getInt("head_count");
				days_attendance.add(headcount);
			}
			
			return days_attendance;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return days_attendance;
	}

	
	public static ArrayList<NandgramAttendance> getWeekAttendance(Date month,Long id){
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
			days_attendance=getRangeAttendance(start.getTime(),end.getTime(),id);
		
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
	
	
	public static ArrayList<Long> getWeekHeadCount(Date month,Long id){
		ArrayList<Long> week_attendance=new ArrayList<Long>();
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
			ArrayList<Long> all_headcount=getRangeHeadCount(start.getTime(),end.getTime(),id);
		
			//if(1==1)return null;
			Iterator<Long> iterator=all_headcount.iterator();
			
			Calendar current=Calendar.getInstance();
			current.setTime(start.getTime());
			Long week=0l;
			while(iterator.hasNext()){
				week+=iterator.next();

				if(current.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY ||!iterator.hasNext()){
					week_attendance.add(week);
					week=0l;
				}
				current.add(Calendar.DATE,1);
			}
			return week_attendance;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return week_attendance;
	}
	
	
	public static ArrayList<Long> getMonthAttendance(Date year, Long nandgram_id){
		ArrayList<Long> month_attendance=new ArrayList<Long>();
		try {
			Calendar start=Calendar.getInstance();
			start.setTime(year);
			start.set(Calendar.MONTH,0);
			start.set(Calendar.DAY_OF_MONTH,1);
			Calendar end=Calendar.getInstance();
			end.setTime(start.getTime());
			end.set(Calendar.MONTH,end.getActualMaximum(Calendar.MONTH));
			end.set(Calendar.DAY_OF_MONTH,end.getActualMaximum(Calendar.DAY_OF_MONTH));
			ArrayList<Long> all_head_counts=getRangeHeadCount(start.getTime(),end.getTime(),nandgram_id);
			Iterator<Long> iterator=all_head_counts.iterator();
			Calendar current=Calendar.getInstance();
			current.setTime(start.getTime());
			Long month=0l;
			while(iterator.hasNext()){
				month+=iterator.next();

				if(current.get(Calendar.DAY_OF_MONTH)==current.getActualMaximum(Calendar.DAY_OF_MONTH) ||!iterator.hasNext()){
					month_attendance.add(month);
					month=0l;
				}
				current.add(Calendar.DATE,1);
			}
			return month_attendance;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return month_attendance;
	}
	
	public static NandgramActivity addActivity(NandgramActivity nandgramActivity){
		
		ArrayList<Image> images = nandgramActivity.getImages();
	
		
		Connection con;
		try {

			con = PostgreSQLConnection.getConnection();
PreparedStatement proc=con.prepareStatement("Select * from  public.\"addActivity\"(?,?,?,?,?)");

			ArrayList<Long> array=new ArrayList<Long>();
			for(Image image:images){
				array.add(image.getImage_id());
			}
			proc.setArray(1,PostgreSQLConnection.getConnection().createArrayOf("bigint", array.toArray()));
			proc.setLong(2, nandgramActivity.getAuthor().getUser_id());
			proc.setLong(3, nandgramActivity.getHeadCount());
			proc.setString(4, nandgramActivity.getText());
			proc.setString(5,nandgramActivity.getActivity());
			System.out.println(proc);
			ResultSet rs=proc.executeQuery();
			rs.next();
			nandgramActivity.setPost_id(rs.getLong(1));;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nandgramActivity;
		}
	

	public static ArrayList<NandgramActivity> getNandgramActivity(Long nandgram_id,Date date) throws SQLException{
		ArrayList<NandgramActivity> activity = new ArrayList<NandgramActivity>();
		Connection con;
		
		con = PostgreSQLConnection.getConnection();
		
		PreparedStatement ps = con.prepareStatement("Select * from \"getNandgramActivity\"(?,?);");
		ps.setLong(1, nandgram_id);
		ps.setDate(2, new java.sql.Date(date.getTime()));
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()){
			NandgramActivity current = new NandgramActivity();
			current.setPost_id(rs.getLong("post_id"));
			current.setHeadCount(rs.getLong("head_count"));
			User user=UserBuilder.getUser(rs.getLong("author"));
			current.setAuthor(user);
			current.setText(rs.getString("text"));
			current.setActivity(rs.getString("activity"));	
			activity.add(current);	
		}
		return activity;
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
