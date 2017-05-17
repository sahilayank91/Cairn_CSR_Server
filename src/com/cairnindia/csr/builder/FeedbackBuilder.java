package com.cairnindia.csr.builder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.cairnindia.csr.database.PostgreSQLConnection;
import com.cairnindia.csr.model.Feedback;
import com.cairnindia.csr.model.Image;
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
	  
public static void main(String[] args) {
}
}
