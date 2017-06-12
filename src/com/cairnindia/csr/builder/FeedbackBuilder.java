package com.cairnindia.csr.builder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.cairnindia.csr.database.PostgreSQLConnection;
import com.cairnindia.csr.model.Feedback;
import com.cairnindia.csr.model.Image;
import com.cairnindia.csr.model.NandgramFeedback;
import com.cairnindia.csr.model.Post;
import com.cairnindia.csr.model.User;


public class FeedbackBuilder {
	 public static void addFeedback(Long user_id,Long admin_id,String text){
				Connection con;
				try {
					con = PostgreSQLConnection.getConnection();
					PreparedStatement proc=con.prepareStatement("Select * from  public.\"addFeedback\"(?,?,?)");
					proc.setLong(1, user_id);
					proc.setLong(2, admin_id);
					proc.setString(3, text);
					
					proc.executeQuery();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	 
	 public static void updateFeedback(Long feedback_id,Integer reaction, String comment){ 
			Connection con;
			try {
				con = PostgreSQLConnection.getConnection();
			    PreparedStatement proc=con.prepareStatement("Select * from  public.\"updateFeedback\"(?,?,?)");
				proc.setLong(1, feedback_id);
				proc.setInt(2,reaction);
				proc.setString(3,comment);
				System.out.println(proc);
				proc.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	 public static void deleteFeedback(Long user_id, Long feedback_id){
			Connection con;
			try {
				con = PostgreSQLConnection.getConnection();
				PreparedStatement proc=con.prepareStatement("Select * from  public.\"deleteFeedback\"(?,?)");
				proc.setLong(1,user_id);
				proc.setLong(2,feedback_id);
				proc.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	 public static ArrayList<Feedback> getFeedbacks(long user_id){
			ArrayList<Feedback> all_feedbacks=new ArrayList<Feedback>();
				try {
					Connection con=PostgreSQLConnection.getConnection();
					PreparedStatement proc=con.prepareStatement("Select * from public.\"getFeedback\"(?);");
					proc.setLong(1,user_id);
					ResultSet rs=proc.executeQuery();
					while(rs.next()){
						Feedback current=new Feedback();
						current.setFeedback_id(rs.getLong("feedback_id"));
						User user=UserBuilder.getUser(rs.getLong("user_id"));
						current.setUser(user);
						User admin=UserBuilder.getUser(rs.getLong("admin"));
						current.setAdmin(admin);
						current.setReaction(rs.getInt("reaction"));
						current.setComment(rs.getString("comment"));
						current.setTime(rs.getTimestamp("timestamp").getTime());
						current.setCompleted(rs.getBoolean("completed"));
						current.setText(rs.getString("text"));
						all_feedbacks.add(current);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return all_feedbacks;
				
	  }
	 
	 
	 public static NandgramFeedback getNandgramFeedback(long nandgram_id) throws SQLException{
			 
		 Connection con = PostgreSQLConnection.getConnection();
		 PreparedStatement ps = con.prepareStatement("Select * from public.\"getNandgramFeedback\"(?);");
		 ps.setLong(1, nandgram_id);
		 ResultSet rs  = ps.executeQuery();
		 NandgramFeedback feedback = new NandgramFeedback();
		
		 while(rs.next()){
			 	feedback.setNandgram_id(rs.getLong("nandgram_id"));
			 	feedback.setHealth_rating(rs.getBigDecimal("health_rating").doubleValue());
			 	feedback.setEducation_rating(rs.getBigDecimal("education_rating").doubleValue());
			 	feedback.setSocial_rating(rs.getBigDecimal("social_rating").doubleValue());
			 	feedback.setWomenskill_rating(rs.getBigDecimal("womenskill_rating").doubleValue());
			 	feedback.setNutrition_rating(rs.getBigDecimal("nutrition_rating").doubleValue());
			 	
			 
			 	Double average = (rs.getBigDecimal("health_rating").doubleValue()+rs.getBigDecimal("education_rating").doubleValue()+rs.getBigDecimal("social_rating").doubleValue()+rs.getBigDecimal("womenskill_rating").doubleValue()+rs.getBigDecimal("nutrition_rating").doubleValue())/5;
			 	feedback.setAverage(average);
			 	System.out.println("Average: " + average);

			 	
			 }
		 
		 return feedback;	 	 
	 }
	 
	 
	 public static NandgramFeedback putNandgramFeedback(NandgramFeedback feed) throws SQLException{
		 
		 Connection con  = PostgreSQLConnection.getConnection();
		 PreparedStatement ps = con.prepareStatement("Select * from public.\"addNandgramFeedback\"(?,?,?,?,?,?);");
		 ps.setLong(1,feed.getNandgram_id());
		 ps.setInt(2,(int) feed.getHealth_rating());
		 ps.setInt(3,(int) feed.getEducation_rating());
		 ps.setInt(4,(int) feed.getSocial_rating());
		 ps.setInt(5,(int)feed.getWomenskill_rating());
		 ps.setInt(6,(int)feed.getNutrition_rating());
		 ps.executeQuery();
		 
		 NandgramFeedback feedback = new NandgramFeedback();
		 feedback = FeedbackBuilder.getNandgramFeedback(feed.getNandgram_id());
		 
		 return feedback;
		 		 
	 }
	  
public static void main(String[] args) {
}
}
