package com.cairnindia.csr.controller;

import java.sql.SQLException;
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
import com.cairnindia.csr.model.NandgramFeedback;
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
	
	@GET
	@Path("/getNandgramFeedback/{nandgram_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public NandgramFeedback getNandgramFeedback(@PathParam("nandgram_id")Long id) throws SQLException{
			NandgramFeedback nandgramFeedback =FeedbackBuilder.getNandgramFeedback(id);	
			return nandgramFeedback;	
	}
	
	@POST
	@Path("/addNandgramFeedback")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public NandgramFeedback putNandgramFeedback(@FormParam("nandgram_id") Long id, @FormParam("health_rating")int health_rating,@FormParam("social_rating")int social_rating,
			@FormParam("education_rating")int education_rating,
			@FormParam("nutrition_rating")int nutrition_rating,
			@FormParam("womenskill_rating")int womenskill_rating) throws SQLException{
		NandgramFeedback feedback = new NandgramFeedback();
		feedback.setNandgram_id(id);
		feedback.setHealth_rating(health_rating);
		feedback.setNutrition_rating(nutrition_rating);
		feedback.setSocial_rating(social_rating);
		feedback.setEducation_rating(education_rating);
		feedback.setWomenskill_rating(womenskill_rating);
		
		feedback = FeedbackBuilder.putNandgramFeedback(feedback);
		
		return feedback;
	}
	
	
}
