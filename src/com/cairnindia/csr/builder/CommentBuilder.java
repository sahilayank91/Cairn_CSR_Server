package com.cairnindia.csr.builder;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.cairnindia.csr.database.PostgreSQLConnection;
import com.cairnindia.csr.model.Comment;
import com.cairnindia.csr.model.User;

public class CommentBuilder {
	public static ArrayList<Comment> getPostComments(long post_id){
		ArrayList<Comment> all_comments=null;
		try {
			Connection con=PostgreSQLConnection.getConnection();
			PreparedStatement proc=con.prepareStatement("Select * from \"getPostComments\"(?);");
			proc.setLong(1, post_id);
			ResultSet rs=proc.executeQuery();
			all_comments=new ArrayList<Comment>();
			while(rs.next()){
				Comment current=new Comment();
				current.setComment_id(rs.getLong("comment_id"));
				User user=UserBuilder.getUser(rs.getLong("comment_author"));
				current.setAuthor(user);
				current.setText(rs.getString("comment_text"));
				current.setTime(rs.getTimestamp("comment_time"));
				all_comments.add(current);	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return all_comments;


	}

	public static void addComment(Comment comment,long post_id){
		Connection con;
		try {
			con = PostgreSQLConnection.getConnection();
			PreparedStatement proc = con.prepareStatement("Select * from public.\"addComment\"(?,?,?);");
			//PreparedStatement proc=con.prepareStatement("Select  public.\"addComment\"(?,?,?);");
			proc.setString(1,comment.getText());
			proc.setLong(2,comment.getAuthor().getUser_id());
			proc.setLong(3,post_id);
			proc.executeQuery(); /*throwing exception*/
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void deleteComment(Long comment_id,Long post_id){
		Connection con;
		try {
			con = PostgreSQLConnection.getConnection();
			CallableStatement proc = con.prepareCall("Select * from public.\"deletePostComment\"(?,?);");
			proc.setLong(1,comment_id);
			proc.setLong(2,post_id);
			proc.executeQuery(); /*throwing exception*/
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
