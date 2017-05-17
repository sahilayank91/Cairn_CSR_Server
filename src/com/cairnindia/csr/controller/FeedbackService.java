package com.cairnindia.csr.controller;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.cairnindia.csr.builder.CommentBuilder;
import com.cairnindia.csr.builder.FeedbackBuilder;
import com.cairnindia.csr.model.Comment;
import com.cairnindia.csr.model.Feedback;
import com.cairnindia.csr.model.User;

@Path("/FeedbackService")
public class FeedbackService {

	@POST
	@Path("/addFeedback")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public void addFeedback(@FormParam("user_id") Long user_id,@FormParam("admin_id") long admin_id,@FormParam("text") String text){
		FeedbackBuilder.addFeedback(user_id, admin_id,text);
	}
	

	@POST
	@Path("/updateFeedback")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean updateFeedback(@FormParam("feedback_id") Long feedback_id,@FormParam("reaction") int reaction, @FormParam("comment") String comment){
		FeedbackBuilder.updateFeedback(feedback_id, reaction, comment);
		return true;
	}
	
	@POST
	@Path("/deleteFeedback")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteFeedback(@FormParam("feedback_id") Long feedback_id,@FormParam("user_id") long user_id){
		FeedbackBuilder.deleteFeedback(user_id, feedback_id);
	}
	
	@GET
	@Path("/getFeedback/{user_id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Feedback>getFeedbacks(@PathParam("user_id") long user_id){
		ArrayList<Feedback> all_feedbacks=FeedbackBuilder.getFeedbacks(user_id);
		return all_feedbacks;
	}
	
	
}
