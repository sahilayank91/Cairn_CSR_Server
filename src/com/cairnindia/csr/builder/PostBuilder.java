package com.cairnindia.csr.builder;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import com.cairnindia.csr.database.PostgreSQLConnection;
import com.cairnindia.csr.model.Comment;
import com.cairnindia.csr.model.Image;
import com.cairnindia.csr.model.Post;
import com.cairnindia.csr.model.User;

public class PostBuilder {
	public static Post addTeamPost(Post post,ArrayList<Long>team_ids){
	
		ArrayList<Image> images = post.getImages();
	
		
		Connection con;
		try {

			con = PostgreSQLConnection.getConnection();
PreparedStatement proc=con.prepareStatement("Select * from  public.\"addTeamsPost\"(?,?,?,?,?)");

			ArrayList<Long> array=new ArrayList<Long>();
			for(Image image:images){
				array.add(image.getImage_id());
			}
			proc.setArray(1,PostgreSQLConnection.getConnection().createArrayOf("bigint", array.toArray()));
			proc.setLong(2, post.getAuthor().getUser_id());
			proc.setLong(3, post.getSmiles());
			proc.setString(4, post.getText());
			proc.setArray(5, PostgreSQLConnection.getConnection().createArrayOf("bigint", team_ids.toArray()));
			System.out.println(proc);
			ResultSet rs=proc.executeQuery();
			rs.next();
			post.setPost_id(rs.getLong(1));;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return post;
		}
	
	public static void addPost(Post post){
		long author_id = post.getAuthor().getUser_id();
		ArrayList<Image> images = post.getImages();
		String text = post.getText();
		long smiles = post.getSmiles();
		long post_id = 0;
		Connection con;
		try {

			con = PostgreSQLConnection.getConnection();
			PreparedStatement proc=con.prepareStatement("Select * from  public.\"addPost\"(?,?,?,?)");

			//proc.setArray(1, PostgreSQLConnection.getConnection().createArrayOf("bigint", images.toArray()));
			ArrayList<Long> array=new ArrayList<Long>();
			for(Image image:images){
				array.add(image.getImage_id());
			}
			proc.setArray(1,PostgreSQLConnection.getConnection().createArrayOf("bigint", array.toArray()));
			proc.setLong(2, author_id);
			proc.setLong(3, smiles);
			proc.setString(4, text);
			//System.out.println(proc);
			proc.executeQuery();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static ArrayList<Post> getAuthorPosts(long author_id){
		ArrayList<Post> all_posts=null;
		try {
			Connection con=PostgreSQLConnection.getConnection();
			PreparedStatement proc=con.prepareStatement("Select * from \"getAuthorPosts\"(?);");
			proc.setLong(1, author_id);
			ResultSet rs=proc.executeQuery();
			all_posts=new ArrayList<Post>();
			while(rs.next()){
				Post current=new Post();
				long post_id=rs.getLong("post_id");
				current.setPost_id(post_id);
				//System.out.println(rs.getLong("post_author"));
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
	public static void addLike(long user_id,long post_id,boolean set_like){
		Connection con=null;
		try {
			con = PostgreSQLConnection.getConnection();
			PreparedStatement proc=con.prepareStatement("SELECT * from  public.\"likeNewsfeedPost\"(?,?,?)");
			proc.setLong(1,post_id);
			proc.setLong(2,user_id);
			proc.setBoolean(3,set_like);
			proc.executeQuery(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static ArrayList<User> getPostLikers(long post_id){
		ArrayList<User> all_users=new ArrayList<User>();
		try {
			Connection con=PostgreSQLConnection.getConnection();
			PreparedStatement proc=con.prepareStatement("Select * from public.\"getPostLikers\"(?);");
			proc.setLong(1,post_id);
			ResultSet rs=proc.executeQuery();
			while(rs.next()){
				User current=new User();
				current.setUser_id(rs.getLong("user_id"));
				current.setEmail(rs.getString("user_email"));
				current.setName(rs.getString("user_name"));
				current.setPhone(rs.getString("user_phone"));	
				all_users.add(current);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return all_users;

	}
	public static void updatePost(Post post, boolean update_image){
		ArrayList<Image> images = post.getImages();
		Connection con;
		try {
			con = PostgreSQLConnection.getConnection();
			PreparedStatement proc=con.prepareStatement("Select * from  public.\"updatePost\"(?,?,?,?,?)");
			//proc.setArray(1, PostgreSQLConnection.getConnection().createArrayOf("bigint", images.toArray()));
			ArrayList<Long> array=new ArrayList<Long>();
			for(Image image:images){
				array.add(image.getImage_id());
			}
			proc.setLong(1, post.getPost_id());
			proc.setLong(2, post.getSmiles());
			proc.setString(3, post.getText());
			proc.setArray(4,PostgreSQLConnection.getConnection().createArrayOf("bigint", array.toArray()));
			proc.setBoolean(5, update_image);
			System.out.println(proc);
			proc.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void sharePost(long post_id,long user_id,ArrayList<Long> team_ids){
		Connection con;
		try {
			con = PostgreSQLConnection.getConnection();
			PreparedStatement proc=con.prepareStatement("Select * from  public.\"sharePost\"(?,?,?)");
			proc.setLong(1, post_id);
			proc.setLong(2,user_id);
			proc.setArray(3, PostgreSQLConnection.getConnection().createArrayOf("bigint", team_ids.toArray()));
			System.out.println(proc);
			proc.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void deleteUserPost(long user_id,long post_id){
		Connection con;
		try {
			con = PostgreSQLConnection.getConnection();
			PreparedStatement proc=con.prepareStatement("Select * from  public.\"deleteUserPost\"(?,?)");
			proc.setLong(1, user_id);
			proc.setLong(2, post_id);
			proc.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void deletePost(long post_id){
		Connection con;
		try {
			con = PostgreSQLConnection.getConnection();
			PreparedStatement proc=con.prepareStatement("Select * from  public.\"deletePost\"(?)");
			proc.setLong(1, post_id);
			proc.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		//	Calendar calendar = Calendar.getInstance();
		//	calendar.set(Calendar.YEAR, 2017);
		//	calendar.set(Calendar.DAY_OF_MONTH, 22);
		//	calendar.set(Calendar.MONTH, 2);
		//	java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
		//	System.out.println(getTeamWeekSmiles(new Date(), 2l));
		//	System.out.println(getTeamMonthSmiles(new Date(), 2l));
		/*	Calendar start_date=Calendar.getInstance();
	start_date.set(Calendar.YEAR, 2017);
	start_date.set(Calendar.DAY_OF_MONTH, 15);
	start_date.set(Calendar.MONTH, 2); 
	java.sql.Date s_date = new java.sql.Date(start_date.getTime().getTime());
	Calendar end_date=Calendar.getInstance();
	end_date.set(Calendar.YEAR, 2017);
	end_date.set(Calendar.DAY_OF_MONTH, 01);
	end_date.set(Calendar.MONTH, 3); 
	java.sql.Date e_date = new java.sql.Date(end_date.getTime().getTime());
    	System.out.println(getDaysSmiles(s_date, e_date, 3l));
		 */}	


}