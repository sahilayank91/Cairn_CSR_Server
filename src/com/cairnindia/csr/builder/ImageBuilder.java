package com.cairnindia.csr.builder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.cairnindia.csr.database.PostgreSQLConnection;
import com.cairnindia.csr.model.Image;

public class ImageBuilder {
	public static Image getImage(long image_id){
		Image current=null;
		try {
			Connection con=PostgreSQLConnection.getConnection();
			PreparedStatement proc=con.prepareStatement("Select * from \"getImage\"(?);");
			proc.setLong(1, image_id);
			ResultSet rs=proc.executeQuery();
			rs.next();
			current=new Image();
				current.setImage_id(rs.getLong("image_id"));
				current.setFilename(rs.getString("filename"));
				current.setCaption(rs.getString("caption"));
				current.setTime(rs.getTimestamp("timestamp"));
				ArrayList<Long> image_likes=new ArrayList<Long>();
				ResultSet likes = rs.getArray("likes").getResultSet();
				while(likes.next()){
					image_likes.add(likes.getLong(2));
				}
				current.setLikes(image_likes);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return current;
		
		
	}

	public static ArrayList<Image> getPostImages(long post_id){
		ArrayList<Image> all_images=null;
		try {
			Connection con=PostgreSQLConnection.getConnection();
			PreparedStatement proc=con.prepareStatement("Select * from \"getPostImages\"(?);");
			proc.setLong(1, post_id);
			ResultSet rs=proc.executeQuery();
			all_images=new ArrayList<Image>();
			while(rs.next()){
				Image current=new Image();
				current.setImage_id(rs.getLong("image_id"));
				current.setFilename(rs.getString("image_filename"));
				current.setCaption(rs.getString("image_caption"));
				current.setTime(rs.getTimestamp("image_time"));
				ArrayList<Long> image_likes=new ArrayList<Long>();
				ResultSet likes = rs.getArray("image_likes").getResultSet();
				while(likes.next()){
					image_likes.add(likes.getLong(2));
				}
				current.setLikes(image_likes);
		       all_images.add(current);	
			}
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return all_images;
	}

	 public static Long addImage(Image image){
				Connection con;
				try {
					con = PostgreSQLConnection.getConnection();
					PreparedStatement proc=con.prepareStatement("Select * from public.\"addImage\"(?,?);");
					proc.setString(1, image.getFilename());
					proc.setString(2, image.getCaption());
				
					 ResultSet rs = proc.executeQuery(); /*throwing exception*/
					if( rs.next()){
						return (rs.getLong(1));
					}
					else return null;
				
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
	 
	
}
