package com.cairnindia.csr.builder;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.cairnindia.csr.database.PostgreSQLConnection;
import com.cairnindia.csr.model.Message;
import com.cairnindia.csr.model.User;
import com.cairnindia.csr.model.UserProfile;
import com.cairnindia.csr.utilities.Hashing;
import com.cairnindia.csr.utilities.Hashing.HashingResult;

public class UserBuilder {
	public static User getUser(long user_id){
		User current=null;
			try {
				Connection con=PostgreSQLConnection.getConnection();
				PreparedStatement proc=con.prepareStatement("Select * from public.\"getUser\"(?);");
				proc.setLong(1, user_id);
				ResultSet rs=proc.executeQuery();
				rs.next();
				current=new User();
					current.setUser_id(rs.getLong("user_id"));
					current.setEmail(rs.getString("email"));
					current.setName(rs.getString("name"));
					current.setPhone(rs.getString("phone"));
					current.setAccount_level(rs.getInt("account_level"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return current;
			
			
		}

	public static ArrayList<User> getUsers(ArrayList<Long> users_id){
		ArrayList<User> all_users=new ArrayList<User>();
			try {
				Connection con=PostgreSQLConnection.getConnection();
				PreparedStatement proc=con.prepareStatement("Select * from public.\"getUsers\"(?);");
				proc.setArray(1,PostgreSQLConnection.getConnection().createArrayOf("bigint", users_id.toArray()));
				ResultSet rs=proc.executeQuery();
				while(rs.next()){
					User current=new User();
					current.setUser_id(rs.getLong("user_id"));
					current.setEmail(rs.getString("user_email"));
					current.setName(rs.getString("user_name"));
					current.setPhone(rs.getString("user_phone"));
					current.setAccount_level(rs.getInt("account_level"));
					all_users.add(current);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return all_users;
			
			
		}

	public static void addUser(User user){
		Connection con;
		try {
			con = PostgreSQLConnection.getConnection();
			CallableStatement proc = con.prepareCall("Select * from public.\"addUser\"(?,?,?,?,?);");
			proc.setString(1,user.getName());
			proc.setString(2,user.getEmail());
			proc.setString(3,user.getHash());
			proc.setString(4,user.getSalt());
			proc.setString(5,user.getPhone());
			System.out.println(proc);
			proc.executeQuery(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void updateUserProfile(UserProfile profile){
		Connection con;
		try {
			con = PostgreSQLConnection.getConnection();
			CallableStatement proc = con.prepareCall("Select * from  public.\"updateUserProfile\"(?,?,?);");
			proc.setLong(1, profile.getUser_id());
			proc.setString(2,profile.getSummary());
			proc.setString(3,profile.getDepartment());
			System.out.println(proc);
			proc.executeQuery(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static UserProfile getUserProfile(long user_id){
		UserProfile profile=null;
			try {
				Connection con=PostgreSQLConnection.getConnection();
				PreparedStatement proc=con.prepareStatement("Select * from public.\"getUserProfile\"(?);");
				proc.setLong(1, user_id);
				ResultSet rs=proc.executeQuery();
				rs.next();
				profile=new UserProfile();
					profile.setDepartment(rs.getString("user_department"));
					profile.setSummary(rs.getString("user_summary"));
					profile.setUser_id(rs.getLong("user_id"));
					profile.setMember_since(rs.getTimestamp("user_member_since"));
					profile.setUser_name(rs.getString("user_name"));
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return profile;
			
			
		}

	public static void addFirebaseToken(User user){
		Connection con;
		try {
			con = PostgreSQLConnection.getConnection();
			CallableStatement proc = con.prepareCall("Select * from public.\"addFirebaseToken\"(?,?);");
			proc.setLong(1,user.getUser_id());
			proc.setString(2,user.getFirebase_token());
			System.out.println(proc);
			proc.executeQuery(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void removeFirebaseToken(User user){
		Connection con;
		try {
			con = PostgreSQLConnection.getConnection();
			CallableStatement proc = con.prepareCall("Select * from public.\"removeFirebaseToken\"(?,?);");
			proc.setLong(1,user.getUser_id());
			proc.setString(2,user.getFirebase_token());
			System.out.println(proc);
			proc.executeQuery(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) {
	/*	UserProfile profile=new UserProfile();
		profile.setUser_id(4l);
		profile.setDepartment("SDE");
		profile.setSummary("summary");
		updateUserProfile(profile);
	*/
		
	}
	
}
