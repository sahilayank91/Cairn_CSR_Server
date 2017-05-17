package com.cairnindia.csr.builder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import com.cairnindia.csr.database.PostgreSQLConnection;
import com.cairnindia.csr.model.Comment;
import com.cairnindia.csr.model.Image;
import com.cairnindia.csr.model.Post;
import com.cairnindia.csr.model.User;

public class NewsfeedBuilder {
	public static ArrayList<Post> getNewsfeedPosts(long user_id){
		ArrayList<Post> all_posts=null;
		try {
			Connection con=PostgreSQLConnection.getConnection();
			PreparedStatement proc=con.prepareStatement("Select * from \"getNewsfeedPosts\"(?);");
			proc.setLong(1, user_id);
			ResultSet rs=proc.executeQuery();
			all_posts=new ArrayList<Post>();
			while(rs.next()){
				Post current=new Post();
				long post_id=rs.getLong("post_id");
				current.setPost_id(post_id);
			
				User user = UserBuilder.getUser(rs.getLong("post_author"));
				current.setAuthor(user);
				current.setSmiles(rs.getLong("post_smiles"));
				current.setText(rs.getString("post_text"));
				current.setTime(rs.getTimestamp("post_time"));
				current.setLikes_count(rs.getLong("post_likes_count"));
				current.setComments_count(rs.getLong("post_comments_count"));

				ArrayList<User> all_likers=NewsfeedBuilder.getPostLikers(post_id);
				current.setLikers(all_likers);
				
				ArrayList<Comment> all_comments=CommentBuilder.getPostComments(post_id);
				current.setComments(all_comments);

				ArrayList<Image> all_images=ImageBuilder.getPostImages(post_id);
				current.setImages(all_images);
				
				all_posts.add(current);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Collections.reverse(all_posts);
		return all_posts;


	}

	public static ArrayList<User> getPostLikers(long post_id){
		ArrayList<User> all_likers=null;
		try {
			Connection con=PostgreSQLConnection.getConnection();
			PreparedStatement proc=con.prepareStatement("Select * from \"getPostLikers\"(?);");
			proc.setLong(1, post_id);
			ResultSet rs=proc.executeQuery();
			all_likers=new ArrayList<User>();
			while(rs.next()){
				User current=new User();
				current.setUser_id(rs.getLong("user_id"));
				current.setName(rs.getString("user_name"));
				current.setEmail(rs.getString("user_email"));
				current.setPhone(rs.getString("user_phone"));
				all_likers.add(current);	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return all_likers;
	}

	public static ArrayList<Post> getUpdateNewsfeedPosts(long user_id,long postid){
		ArrayList<Post> all_posts=null;
		try {
			Connection con=PostgreSQLConnection.getConnection();
			PreparedStatement proc=con.prepareStatement("Select * from \"getUpdateNewsfeedPosts\"(?,?);");
			proc.setLong(1, user_id);
			proc.setLong(2, postid);
			ResultSet rs=proc.executeQuery();
			all_posts=new ArrayList<Post>();
			while(rs.next()){
				Post current=new Post();
				long post_id=rs.getLong("post_id");
				current.setPost_id(post_id);
				User user = UserBuilder.getUser(rs.getLong("post_author"));
				current.setAuthor(user);
				current.setSmiles(rs.getLong("post_smiles"));
				current.setText(rs.getString("post_text"));
				current.setTime(rs.getTimestamp("post_time"));
				current.setLikes_count(rs.getLong("post_likes_count"));
				current.setComments_count(rs.getLong("post_comments_count"));

				ArrayList<User> all_likers=NewsfeedBuilder.getPostLikers(post_id);
				current.setLikers(all_likers);
				
				ArrayList<Comment> all_comments=CommentBuilder.getPostComments(post_id);
				current.setComments(all_comments);

				ArrayList<Image> all_images=ImageBuilder.getPostImages(post_id);
				current.setImages(all_images);
				
				all_posts.add(current);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return all_posts;


	}

}

	
	

/*ResultSet likes=rs.getArray("post_likes").getResultSet();
ArrayList<Long> all_likes=new ArrayList<Long>();
while(likes.next()){
	all_likes.add(likes.getLong(2));
}*/