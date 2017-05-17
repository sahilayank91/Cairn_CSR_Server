package com.cairnindia.csr.controller;

import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.cairnindia.csr.builder.ImageBuilder;
import com.cairnindia.csr.builder.NewsfeedBuilder;
import com.cairnindia.csr.builder.PostBuilder;
import com.cairnindia.csr.builder.UserBuilder;
import com.cairnindia.csr.model.Image;
import com.cairnindia.csr.model.Post;
import com.cairnindia.csr.model.User;

@Path("/NewsfeedService")
public class NewsfeedService {

	/*	@POST //GET or post
	   @Path("/Login") 
	   @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	   @Produces(MediaType.APPLICATION_JSON)
	public long login(@PathParam("user_id")Long user_id) throws JSONException{
		//User user=new User(user_id);
		NewsfeedBuilder.getNewsfeedPosts(user_id);
		return user_id;
	}*/
	
/*	@GET
	@Path("/Test/{user_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Long test(@PathParam("user_id")Long user_id){
		//NewsfeedBuilder.getNewsfeedPosts(user_id);
		return user_id;
	}*/
	
	@GET
	@Path("/RetrieveNewsfeedPosts/{user_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Post> getNewsfeedPosts(@PathParam("user_id")Long user_id){
		ArrayList<Post> posts=NewsfeedBuilder.getNewsfeedPosts(user_id);
		
		return posts;
	}
	@GET
	@Path("/RetrieveAuthorPosts/{user_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Post> getAuthorPosts(@PathParam("user_id")Long user_id){
		ArrayList<Post> posts=PostBuilder.getAuthorPosts(user_id);
		
		return posts;
	}
	
	@GET
	@Path("/RetrieveUpdateNewsfeedPosts/{user_id}/{post_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Post> getUpdateNewsfeedPosts(@PathParam("user_id")Long user_id,@PathParam("post_id")Long post_id){
		ArrayList<Post> posts=NewsfeedBuilder.getUpdateNewsfeedPosts(user_id,post_id);
		System.out.println("success");
		return posts;
	}
	
	@GET
	@Path("/RetrieveImage/{image_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Image getImage(@PathParam("image_id")Long image_id){
		Image image=ImageBuilder.getImage(image_id);
		return image;
	}
	
	@GET
	@Path("/RetrievePostLikers/{post_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<User> getPostLikers(@PathParam("post_id")Long post_id){
		ArrayList<User> all_likers = NewsfeedBuilder.getPostLikers(post_id);
		return all_likers;
	}
	@GET
	@Path("/RetrieveUser/{user_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("user_id")Long user_id){
		User user_data= UserBuilder.getUser(user_id);
		return user_data;
	}
	
	
}
