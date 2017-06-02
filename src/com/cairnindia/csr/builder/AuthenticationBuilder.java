package com.cairnindia.csr.builder;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cairnindia.csr.database.PostgreSQLConnection;
import com.cairnindia.csr.model.User;
public class AuthenticationBuilder {

	public static User getUser(String user_email){
		User user=null;
		try {
			Connection con=PostgreSQLConnection.getConnection();
			PreparedStatement proc=con.prepareStatement("Select * from \"authenticate\"(?);");
			//Select * from is important to retrieve multiple columns
			//enclose function name in escaped double quotes to allow case-sensitive
			proc.setString(1, user_email);
			ResultSet rs=proc.executeQuery();
			System.out.println(proc);
			if(rs.next()){
				user=new User();
				user.setUser_id(rs.getLong("user_id"));
				user.setName(rs.getString("name"));
				user.setPhone(rs.getString("phone"));
				user.setEmail(rs.getString("email"));
				user.setHash(rs.getString("hash"));
				user.setSalt(rs.getString("salt"));
				user.setAccount_level(rs.getInt("account_level"));
				user.setVerified(rs.getBoolean("verified"));
				return user;
			}
			else return null;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static void updatePassword(User user){
		Connection con;
		try {
			con = PostgreSQLConnection.getConnection();
			CallableStatement proc = con.prepareCall("Select * from public.\"updatePassword\"(?,?,?);");
		
			proc.setLong(1,user.getUser_id());
			proc.setString(2,user.getHash());
			proc.setString(3,user.getSalt());
			System.out.println(proc);
			proc.executeQuery(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void registerFirebaseToken(Long user_id,String token){
		
	}


}
