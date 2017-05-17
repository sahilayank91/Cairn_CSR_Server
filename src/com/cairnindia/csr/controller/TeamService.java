package com.cairnindia.csr.controller;

import java.util.ArrayList;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.cairnindia.csr.builder.MessageBuilder;
import com.cairnindia.csr.builder.TeamBuilder;
import com.cairnindia.csr.model.Message;
import com.cairnindia.csr.model.Team;
import com.cairnindia.csr.model.User;

@Path("/TeamService")
public class TeamService {

	@GET
	@Path("/RetrieveTeamMessages/{team_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Message> getTeamMessages(@PathParam("team_id")Long team_id){
		ArrayList<Message> user_data= MessageBuilder.getTeamMessages(team_id);
		return user_data;
	}
	@GET
	@Path("/RetrieveUserTeams/{user_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Team> getUserTeams(@PathParam("user_id") Long user_id){
		ArrayList<Team> all_teams= TeamBuilder.getUserTeams(user_id);
		return all_teams;
	}
	@GET
	@Path("/RetrieveTeams/")
	@Produces(MediaType.APPLICATION_JSON)

	public ArrayList<Team> getTeams(){
		ArrayList<Team> all_teams= TeamBuilder.getTeams();
		return all_teams;
	}
	@GET
	@Path("/RetrieveTeamMembers/{team_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<User> getTeamMembers(@PathParam("team_id") long team_id){
		ArrayList<User> all_members= TeamBuilder.getTeamMembers(team_id);
		return all_members;
	}

	@POST
	@Path("/AddTeamMessage")
	@Produces(MediaType.APPLICATION_JSON)
	public Message addTeamMessage(@FormParam("message_text")String message_text,@FormParam("user_id")Long user_id,@FormParam("team_id") long team_id){
		Message message=new Message();
		message.setText(message_text);
		User user=new User();
		user.setUser_id(user_id);
		message.setAuthor(user);
		return MessageBuilder.addTeamMessage(message, team_id);
	}


	@POST
	@Path("/AddTeam")
	@Produces(MediaType.APPLICATION_JSON)
	public void addTeam(@FormParam("team_name")String team_name,@FormParam("user_id")Long user_id,@FormParam("team_id") long team_id){
//		Message message=new Message();
//		message.setText(message_text);
//		User user=new User();
//		user.setUser_id(user_id);
//		message.setAuthor(user);
	//	TeamBuilder.addTeamMessage(message, team_id);;
	}

}
