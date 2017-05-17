package com.cairnindia.csr.builder;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.cairnindia.csr.database.PostgreSQLConnection;
import com.cairnindia.csr.model.Message;
import com.cairnindia.csr.model.User;

public class MessageBuilder {
	public static void main(String[] args) {
		System.out.println(getTeamMessages(1));
	}
	public static Message addTeamMessage(Message message,long team_id){
		Connection con;
		try {
			con = PostgreSQLConnection.getConnection();
			CallableStatement proc = con.prepareCall("Select * from public.\"addTeamMessage\"(?,?,?);");
			proc.setString(1,message.getText());
			proc.setLong(2,message.getAuthor().getUser_id());
			proc.setLong(3,team_id);
		ResultSet rs=	proc.executeQuery(); 
		rs.next();
		message.setMessage_id(rs.getLong(1));
		message.setTime(new Date());
		return message;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<Message> getTeamMessages(long team_id){
		ArrayList<Message> all_messages=new ArrayList<Message>();
		try {
			Connection con=PostgreSQLConnection.getConnection();
			PreparedStatement proc=con.prepareStatement("Select * from \"getTeamMessages\"(?);");
			proc.setLong(1,team_id);
			ResultSet rs=proc.executeQuery();
			while(rs.next()){
				Message current=new Message();
				current.setMessage_id(rs.getLong("message_id"));
				User user=UserBuilder.getUser(rs.getLong("message_author"));
				current.setAuthor(user);
				current.setText(rs.getString("message_text"));
				current.setTime(rs.getTimestamp("message_time"));
				all_messages.add(current);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return all_messages;


	}

}
