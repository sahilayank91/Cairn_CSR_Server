package com.cairnindia.csr.builder;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import com.cairnindia.csr.database.PostgreSQLConnection;
import com.cairnindia.csr.model.Message;
import com.cairnindia.csr.model.Team;
import com.cairnindia.csr.model.User;

public class TeamBuilder {
	

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

	public static void addTeam(Team team){
		Connection con;
		ArrayList<Long> admins=new ArrayList<Long>();
		Iterator<User> admins_iterator = team.getAdmins().iterator();
		while(admins_iterator.hasNext()){
			admins.add(admins_iterator.next().getUser_id());
		}
		ArrayList<Long> members=new ArrayList<Long>();
		Iterator<User> member_iterator = team.getMembers().iterator();
		while(member_iterator.hasNext()){
			members.add(member_iterator.next().getUser_id());
		}

		try {
			con = PostgreSQLConnection.getConnection();
			CallableStatement proc = con.prepareCall("Select * from  public.\"addTeam\"(?,?,?,?);");
			proc.setString(1,team.getName());
			proc.setArray(2,PostgreSQLConnection.getConnection().createArrayOf("bigint", admins.toArray()));
			proc.setArray(3,PostgreSQLConnection.getConnection().createArrayOf("bigint", members.toArray()));
			proc.setString(4,team.getDescription());
			proc.executeQuery(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void addTeamMember(Long member_id,Long team_id){
		Connection con;
				try {
			con = PostgreSQLConnection.getConnection();
			PreparedStatement proc = con.prepareStatement("Select * from  public.\"addTeamMember\"(?,?);");
			proc.setLong(1,member_id);
			proc.setLong(2,team_id);
			proc.executeQuery(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static ArrayList<Team> getTeams(){
		ArrayList<Team> all_teams=null;
		try {
			Connection con=PostgreSQLConnection.getConnection();
			PreparedStatement proc=con.prepareStatement("Select * from \"getTeams\"();");
			ResultSet rs=proc.executeQuery();
			all_teams=new ArrayList<Team>();
			while(rs.next()){
				Team current=new Team();
				long team_id=rs.getLong("team_id");
				current.setTeam_id(team_id);
				current.setDescription(rs.getString("description"));
                current.setName(rs.getString("team_name"));
				
				ResultSet admins=rs.getArray("admins").getResultSet();
				ArrayList<Long> admins_id=new ArrayList<Long>();
				while(admins.next()){
					admins_id.add(admins.getLong(2));
				}
				ResultSet members=rs.getArray("members").getResultSet();
				ArrayList<Long> members_id=new ArrayList<Long>();
				while(members.next()){
					members_id.add(members.getLong(2));
				}
				/*ResultSet messages=rs.getArray("messages").getResultSet();
				ArrayList<Long> messages_id=new ArrayList<Long>();
				while(messages.next()){
					messages_id.add(messages.getLong(2));
				}  
				ArrayList<Message> all_messages = TeamBuilder.getTeamMessages(team_id);
				current.setMessages(all_messages); */
				ArrayList<User> all_members=UserBuilder.getUsers(members_id);
				current.setMembers(all_members);
				ArrayList<User> all_admins=UserBuilder.getUsers(admins_id);
				current.setAdmins(all_admins);
				all_teams.add(current);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return all_teams;
	}

	public static ArrayList<Team> getUserTeams(long user_id){
		ArrayList<Team> user_teams=null;
		try {
			Connection con=PostgreSQLConnection.getConnection();
			PreparedStatement proc=con.prepareStatement("Select * from \"getUserTeams\"(?);");
			proc.setLong(1, user_id);
			ResultSet rs=proc.executeQuery();
			System.out.println(proc);
			user_teams=new ArrayList<Team>();
			while(rs.next()){
				Team current=new Team();
				long team_id=rs.getLong("team_id");
				current.setTeam_id(team_id);
				current.setDescription(rs.getString("description"));
                current.setName(rs.getString("team_name"));
				
				ResultSet admins=rs.getArray("admins").getResultSet();
				ArrayList<Long> admins_id=new ArrayList<Long>();
				while(admins.next()){
					admins_id.add(admins.getLong(2));
				}
				ResultSet members=rs.getArray("members").getResultSet();
				ArrayList<Long> members_id=new ArrayList<Long>();
				while(members.next()){
					members_id.add(members.getLong(2));
				}
				/*ResultSet messages=rs.getArray("messages").getResultSet();
				ArrayList<Long> messages_id=new ArrayList<Long>();
				while(messages.next()){
					messages_id.add(messages.getLong(2));
				}  
				ArrayList<Message> all_messages = TeamBuilder.getTeamMessages(team_id);
				current.setMessages(all_messages); */
				ArrayList<User> all_members=UserBuilder.getUsers(members_id);
				current.setMembers(all_members);
				ArrayList<User> all_admins=UserBuilder.getUsers(admins_id);
				current.setAdmins(all_admins);
				user_teams.add(current);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user_teams;
	}
	public static ArrayList<Team> getUserTeamList(long user_id){
		ArrayList<Team> user_teams=null;
		try {
			Connection con=PostgreSQLConnection.getConnection();
			PreparedStatement proc=con.prepareStatement("Select * from \"getUserTeamList\"(?);");
			proc.setLong(1, user_id);
			ResultSet rs=proc.executeQuery();
			user_teams=new ArrayList<Team>();
			while(rs.next()){
				Team current=new Team();
				long team_id=rs.getLong("team_id");
				current.setTeam_id(team_id);
				current.setDescription(rs.getString("description"));
                current.setName(rs.getString("team_name"));
				ResultSet admins=rs.getArray("admins").getResultSet();
				ArrayList<Long> admins_id=new ArrayList<Long>();
				while(admins.next()){
					admins_id.add(admins.getLong(2));
				}
				ResultSet members=rs.getArray("members").getResultSet();
				ArrayList<Long> members_id=new ArrayList<Long>();
				while(members.next()){
					members_id.add(members.getLong(2));
				}
				ArrayList<User> all_members=UserBuilder.getUsers(members_id);
				current.setMembers(all_members);
				ArrayList<User> all_admins=UserBuilder.getUsers(admins_id);
				current.setAdmins(all_admins);
				user_teams.add(current);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user_teams;
	}
		public static ArrayList<User> getTeamMembers(long team_id){
		ArrayList<User> all_members=new ArrayList<User>();
			try {
				Connection con=PostgreSQLConnection.getConnection();
				PreparedStatement proc=con.prepareStatement("Select * from \"getTeamMembers\"(?);");
				proc.setLong(1,team_id);
				ResultSet rs=proc.executeQuery();
				while(rs.next()){
					User current=new User();
					current.setUser_id(rs.getLong("user_id"));
					current.setEmail(rs.getString("user_email"));
					current.setName(rs.getString("user_name"));
					current.setPhone(rs.getString("user_phone"));	
					all_members.add(current);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return all_members;
			
			
		}

	
	

}
