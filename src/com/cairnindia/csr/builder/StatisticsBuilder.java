package com.cairnindia.csr.builder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import com.cairnindia.csr.database.PostgreSQLConnection;

public class StatisticsBuilder {
	public static Long getDaySmiles(Date date,Long userid){
		Long smiles=0l;
		try {
			Connection con=PostgreSQLConnection.getConnection();
			PreparedStatement proc=con.prepareStatement("Select * from \"getDaySmiles\"(?,?);");
			proc.setLong(1, userid);
			proc.setDate(2, new java.sql.Date(date.getTime()));
			ResultSet rs=proc.executeQuery();
			rs.next();
			smiles=rs.getLong(1);
			return smiles;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return smiles;
	}
	public static ArrayList<Long> getRangeSmiles(Date start_date,Date end_date, Long userid){
		ArrayList<Long> days_smiles=new ArrayList<Long>();
		try {
			Connection con=PostgreSQLConnection.getConnection();
			PreparedStatement proc=con.prepareStatement("Select * from \"getDaysSmiles\"(?,?,?);");
			proc.setLong(1, userid);
			proc.setDate(2, new java.sql.Date(start_date.getTime()));
			proc.setDate(3, new java.sql.Date(end_date.getTime()));
		
			ResultSet rs=proc.executeQuery();
			while(rs.next()){
				Long current=rs.getLong("days_smiles");
				days_smiles.add(current);
			}
			return days_smiles;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return days_smiles;
	}

	public static ArrayList<Long> getDaywiseRangeDates(Date start_date,Date end_date){
		ArrayList<Long> dates=new ArrayList<Long>();
		
		Calendar end=Calendar.getInstance();
		end.setTime(end_date);
		end.set(Calendar.HOUR, 0);
		end.set(Calendar.MINUTE, 0);
		end.set(Calendar.SECOND, 0);
		end.set(Calendar.MILLISECOND, 0);
		
		Calendar cal=Calendar.getInstance();
		cal.setTime(start_date);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
	
		while(cal.getTimeInMillis()<=end.getTimeInMillis()){
			dates.add(cal.getTime().getTime());
			cal.add(Calendar.DATE,1);
		

		}
		return dates;

	}
	public static ArrayList<Long> getWeekwiseRangeDates(Date start_date){
		ArrayList<Long> dates=new ArrayList<Long>();
		Calendar cal=Calendar.getInstance();
		cal.setTime(start_date);
		cal.set(Calendar.DATE,1);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Calendar end=Calendar.getInstance();
		end.setTime(start_date);
		end.set(Calendar.DATE,end.getActualMaximum(Calendar.DATE));
		end.set(Calendar.HOUR, 23);
		end.set(Calendar.MINUTE, 59);
		end.set(Calendar.SECOND, 59);
		end.set(Calendar.MILLISECOND,999);
		Date end_date=end.getTime();
		boolean add=true;
		while(cal.getTime().before(end_date)){
			if(add){
				dates.add(cal.getTime().getTime());
				add=false;
			}

			if(cal.get(Calendar.DAY_OF_WEEK)!=Calendar.SATURDAY){
				cal.add(Calendar.DATE,1);
			}
			else{
				add=true;
				cal.add(Calendar.DATE,1);
			}


		}
		return dates;

	}

	public static ArrayList<Long> getMonthwiseRangeDates(Date start_date){
		ArrayList<Long> dates=new ArrayList<Long>();
		Calendar cal=Calendar.getInstance();
		cal.setTime(start_date);
		cal.set(Calendar.DATE,1);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Calendar end=Calendar.getInstance();
		end.setTime(start_date);
		end.add(Calendar.YEAR,1);
		end.set(Calendar.HOUR, 23);
		end.set(Calendar.MINUTE, 59);
		end.set(Calendar.SECOND, 59);
		end.set(Calendar.MILLISECOND,999);
		Date end_date=end.getTime();

		while(cal.getTime().before(end_date)){

			dates.add(cal.getTime().getTime());

			cal.add(Calendar.MONTH,1);
		}
		return dates;

	}

	public static Long getTeamDaySmiles(Date date,Long teamid){
		Long smiles=0l;
		try {
			Connection con=PostgreSQLConnection.getConnection();
			PreparedStatement proc=con.prepareStatement("Select * from \"getTeamDaySmiles\"(?,?);");
			proc.setLong(1, teamid);
			proc.setDate(2, new java.sql.Date(date.getTime()));
			ResultSet rs=proc.executeQuery();
			rs.next();
			smiles=rs.getLong(1);
			return smiles;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return smiles;
	}
	public static ArrayList<Long> getRangeTeamSmiles(Date start_date,Date end_date, Long teamid){
		ArrayList<Long> days_smiles=new ArrayList<Long>();
		try {
			Connection con=PostgreSQLConnection.getConnection();
			PreparedStatement proc=con.prepareStatement("Select * from \"getTeamDaysSmiles\"(?,?,?);");
			proc.setLong(1, teamid);
			proc.setDate(2, new java.sql.Date(start_date.getTime()));
			proc.setDate(3,  new java.sql.Date(end_date.getTime()));
			ResultSet rs=proc.executeQuery();
			while(rs.next()){
				Long current=rs.getLong("days_smiles");
				days_smiles.add(current);
			}
			return days_smiles;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return days_smiles;
	}

	public static ArrayList<Long> getWeekSmiles(Date month, Long userid){
		ArrayList<Long> week_smiles=new ArrayList<Long>();
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
			ArrayList<Long> all_smiles=getRangeSmiles(start.getTime(),end.getTime(),userid);
		
			//if(1==1)return null;
			Iterator<Long> iterator=all_smiles.iterator();
			
			Calendar current=Calendar.getInstance();
			current.setTime(start.getTime());
			Long week=0l;
			while(iterator.hasNext()){
				week+=iterator.next();

				if(current.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY ||!iterator.hasNext()){
					week_smiles.add(week);
					week=0l;
				}
				current.add(Calendar.DATE,1);

			}




			return week_smiles;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return week_smiles;
	}
	public static ArrayList<Long> getTeamWeekSmiles(Date month, Long userid){
		ArrayList<Long> week_smiles=new ArrayList<Long>();
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
			ArrayList<Long> all_smiles=getRangeTeamSmiles(start.getTime(),end.getTime(),userid);
			//if(1==1)return null;
			Iterator<Long> iterator=all_smiles.iterator();
			Calendar current=Calendar.getInstance();
			current.setTime(start.getTime());
			Long week=0l;
			while(iterator.hasNext()){
				week+=iterator.next();

				if(current.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY ||!iterator.hasNext()){
					week_smiles.add(week);
					week=0l;
				}
				current.add(Calendar.DATE,1);
			}	
			return week_smiles;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return week_smiles;
	}

	public static ArrayList<Long> getMonthSmiles(Date year, Long userid){
		ArrayList<Long> month_smiles=new ArrayList<Long>();
		try {
			Calendar start=Calendar.getInstance();
			start.setTime(year);
			start.set(Calendar.MONTH,0);
			start.set(Calendar.DAY_OF_MONTH,1);
			Calendar end=Calendar.getInstance();
			end.setTime(start.getTime());
			end.set(Calendar.MONTH,end.getActualMaximum(Calendar.MONTH));
			end.set(Calendar.DAY_OF_MONTH,end.getActualMaximum(Calendar.DAY_OF_MONTH));
			ArrayList<Long> all_smiles=getRangeSmiles(start.getTime(),end.getTime(),userid);

			Iterator<Long> iterator=all_smiles.iterator();
			Calendar current=Calendar.getInstance();
			current.setTime(start.getTime());
			Long month=0l;
			while(iterator.hasNext()){
				month+=iterator.next();

				if(current.get(Calendar.DAY_OF_MONTH)==current.getActualMaximum(Calendar.DAY_OF_MONTH) ||!iterator.hasNext()){
					month_smiles.add(month);
					month=0l;
				}
				current.add(Calendar.DATE,1);
			}
			return month_smiles;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return month_smiles;
	}

	public static ArrayList<Long> getTeamMonthSmiles(Date year, Long userid){
		ArrayList<Long> month_smiles=new ArrayList<Long>();
		try {
			Calendar start=Calendar.getInstance();
			start.setTime(year);
			start.set(Calendar.MONTH,0);
			start.set(Calendar.DAY_OF_MONTH,1);
			Calendar end=Calendar.getInstance();
			end.setTime(start.getTime());
			end.set(Calendar.MONTH,end.getActualMaximum(Calendar.MONTH));
			end.set(Calendar.DAY_OF_MONTH,end.getActualMaximum(Calendar.DAY_OF_MONTH));
			ArrayList<Long> all_smiles=getRangeTeamSmiles(start.getTime(),end.getTime(),userid);

			Iterator<Long> iterator=all_smiles.iterator();
			Calendar current=Calendar.getInstance();
			current.setTime(start.getTime());
			Long month=0l;
			while(iterator.hasNext()){
				month+=iterator.next();

				if(current.get(Calendar.DAY_OF_MONTH)==current.getActualMaximum(Calendar.DAY_OF_MONTH) ||!iterator.hasNext()){
					month_smiles.add(month);
					month=0l;
				}
				current.add(Calendar.DATE,1);
			}
			return month_smiles;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return month_smiles;
	}

}
