package com.cairnindia.csr.controller;


import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.FileHandler;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.swing.text.Utilities;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;







import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.imgscalr.Scalr;

import com.cairnindia.csr.builder.CommentBuilder;
import com.cairnindia.csr.builder.ImageBuilder;
import com.cairnindia.csr.builder.PostBuilder;
import com.cairnindia.csr.builder.TeamBuilder;
import com.cairnindia.csr.model.Comment;
import com.cairnindia.csr.model.Image;
import com.cairnindia.csr.model.Post;
import com.cairnindia.csr.model.Team;
import com.cairnindia.csr.model.User;

@Path("/PostService")
public class PostService {
    private static final String FILE_UPLOAD_PATH = "/home/cairn/cairn_csr/images";

	
	
	@POST
	@Path("/addComment")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public void addComment(@FormParam("comment_text") String text, @FormParam("comment_author") long author,@FormParam("post_id") long post_id){
		Comment comment=new Comment();
		User user=new User();
		user.setUser_id(author);
		comment.setAuthor(user);
		comment.setText(text);
		CommentBuilder.addComment(comment,post_id);
	}
	@POST
	@Path("/deleteComment")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public void addComment( @FormParam("comment_id") long comment_id,@FormParam("post_id") long post_id){

		CommentBuilder.deleteComment(comment_id,post_id);
	}
	@POST
	@Path("/savefile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public void addPost(@Context HttpServletRequest request)
	{
		
		Post post=new Post();
		User user=new User();
		ArrayList<Image>images=new ArrayList<Image>();
	
		
		
        String name = null;
        int code = 200;
        String msg = "Files uploaded successfully";
        if (ServletFileUpload.isMultipartContent(request)) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            try {
                List<FileItem> items =  fileUpload.parseRequest(request);
                if (items != null) {
                    Iterator<FileItem> iter = items.iterator();
                    /*
                     * Return true if the instance represents a simple form
                     * field. Return false if it represents an uploaded file.
                     */
                    while (iter.hasNext()) {
                        final FileItem item = iter.next();
                        String itemName = item.getName();
                        final String fieldName = item.getFieldName();
                        final String fieldValue = item.getString();
                        if (item.isFormField()) {
                            name = fieldValue;
                            System.out.println("Field Name: " + fieldName + ", Field Value: " + fieldValue);
                            System.out.println("Candidate Name: " + name);
                            switch(fieldName){
                            case "description":
                            	post.setText(fieldValue);
                            
                            	break;
                            case "author":
                            	user.setUser_id(Long.valueOf(fieldValue));
                        		post.setAuthor(user);
                            	break;
                            case "smiles":
                            	post.setSmiles(Long.valueOf(fieldValue));
                            	break;
                            	
                            }
                        } else {
                            Image image=new Image();
                            image.setFilename("img_"+new Date().getTime()+".jpg");
                            
                        	itemName=image.getFilename();
                            final File file = new File(FILE_UPLOAD_PATH    + File.separator + itemName);
                          
                            File dir = file.getParentFile();
                            if(!dir.exists()) {
                                dir.mkdir();
                            }
                            
                            if(!file.exists()) {
                                file.createNewFile();
                            }
                            System.out.println("Saving the file: " + file.getName());
                    
                            
                          Long image_id=  ImageBuilder.addImage(image);
                          image.setImage_id(image_id);
                          System.out.println(image_id);
                          if(image_id!=null)images.add(image);
              
                            	FileOutputStream outputStream = new FileOutputStream(file);
                                 
                                // reads input image from file
                           BufferedImage inputImage = ImageIO.read(item.getInputStream());
                           inputImage = Scalr.resize(inputImage, 500, 300);
                                // writes to the output image in specified format
                                boolean result = ImageIO.write(inputImage, "JPG", outputStream);
                               
                                 
                        
                            
                                outputStream.close();
                               
                          
                        }
                    }
                }
            } catch (FileUploadException e) {
                code = 404;
                msg = e.getMessage();
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
                code = 404;
                msg = e.getMessage();
            }
        }
    	
        post.setImages(images);
		PostBuilder.addPost(post);
        return;
    }
	
	
	@POST
	@Path("/AddLike")
	@Produces(MediaType.APPLICATION_JSON)
public boolean addLike(@FormParam("user_id")Long user_id,@FormParam("post_id")Long post_id,@FormParam("set_like")int set_like){
		PostBuilder.addLike(user_id, post_id, set_like==1);

		return set_like==1;
	}	

	@GET
	@Path("/RetrievePostLikers/{post_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<User> getPostLikers(@PathParam("post_id") Long post_id){
		ArrayList<User> all_likers= PostBuilder.getPostLikers(post_id);
		return all_likers;
	}
	
	@GET
	@Path("/RetrievePostComments/{post_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Comment> getPostComments(@PathParam("post_id") Long post_id){
		ArrayList<Comment> all_comments= CommentBuilder.getPostComments(post_id);
		return all_comments;
	}

	
	
	@POST
	@Path("/SharePost")
	@Produces(MediaType.APPLICATION_JSON)
public boolean sharePost(@FormParam("user_id")Long user_id,@FormParam("post_id")Long post_id,@FormParam("teams")String team_array){
		String[] array = team_array.split(",");
	ArrayList<Long> team_ids=new ArrayList<>();
		for(int i=0;i<array.length;i++){
			team_ids.add(Long.valueOf(array[i]));
		}
				
		PostBuilder.sharePost( post_id,user_id,team_ids);

		return true;
	}
	
	
	@POST
	@Path("/updatePost")
	@Consumes(MediaType.MULTIPART_FORM_DATA+ ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON)
	public Post updatePost(@Context HttpServletRequest request)
	{
		
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		boolean update_image=false;
		Post post=new Post();
		User user=new User();
		ArrayList<Image>images=new ArrayList<Image>();
	
		
		
        String name = null;
        int code = 200;
        String msg = "Files uploaded successfully";
        if (ServletFileUpload.isMultipartContent(request)) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            try {
            	
                List<FileItem> items =  fileUpload.parseRequest(request);
                if (items != null) {
                    Iterator<FileItem> iter = items.iterator();
                    /*
                     * Return true if the instance represents a simple form
                     * field. Return false if it represents an uploaded file.
                     */
                    while (iter.hasNext()) {
                        final FileItem item = iter.next();
                        String itemName = item.getName();
                        final String fieldName = item.getFieldName();
                        final String fieldValue = item.getString();
                        if (item.isFormField()) {
                            name = fieldValue;
                            System.out.println("Field Name: " + fieldName + ", Field Value: " + item.getString("UTF-8").trim());
                            System.out.println("Candidate Name: " + name);
                            switch(fieldName){
                            case "description":
                     
                            	post.setText(item.getString("UTF-8").trim());
                            
                            	break;
                            case "author":
                            	user.setUser_id(Long.valueOf(fieldValue));
                        		post.setAuthor(user);
                            	break;
                            case "head_count":
                            	post.setSmiles(Long.valueOf(fieldValue));
                            	break;
                            case "post_id":
                            	post.setPost_id(Long.valueOf(fieldValue));
                            	break;
                            case "update_image":
                            	int temp=Integer.valueOf(fieldValue);
                            	update_image=(temp==1);
                            	break;
                            	
                            }
                        } else {
                            Image image=new Image();
                            image.setFilename("img_"+new Date().getTime()+".jpg");
                          
                        	itemName=image.getFilename();
                            final File file = new File(FILE_UPLOAD_PATH    + File.separator + itemName);
                          
                            File dir = file.getParentFile();
                            if(!dir.exists()) {
                                dir.mkdir();
                            }
                            
                            if(!file.exists()) {
                                file.createNewFile();
                            }
                            System.out.println("Saving the file: " + file.getName());
                    
                            
                          Long image_id=  ImageBuilder.addImage(image);
                          image.setImage_id(image_id);
                          System.out.println(image_id);
                          if(image_id!=null)images.add(image);
              
                            	FileOutputStream outputStream = new FileOutputStream(file);
                                 
                                // reads input image from file
                           BufferedImage inputImage = ImageIO.read(item.getInputStream());
                           inputImage = Scalr.resize(inputImage, 500, 300);
                                // writes to the output image in specified format
                                boolean result = ImageIO.write(inputImage, "JPG", outputStream);
                               
                                 
                        
                            
                                outputStream.close();
                               
                          
                        }
                    }
                }
                post.setImages(images);
        		PostBuilder.updatePost(post,update_image);  
            } catch (FileUploadException e) {
                code = 404;
                msg = e.getMessage();
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
                code = 404;
                msg = e.getMessage();
            }
        }
    	
      
        return post;
    }

	
	@POST
	@Path("/deletePost")
	@Produces(MediaType.APPLICATION_JSON)
public boolean deletePost(@FormParam("post_id")Long post_id){
		PostBuilder.deletePost( post_id);

		return true;
	}
	@POST
	@Path("/addPost")
	@Consumes(MediaType.MULTIPART_FORM_DATA+ "; charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON)

	public Post addTeamPost(@Context HttpServletRequest request)
	{
		
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Post post=new Post();
		User user=new User();
		ArrayList<Image>images=new ArrayList<Image>();
		ArrayList<Long>team_ids=new ArrayList<>();
	
		
		
        String name = null;
        int code = 200;
        String msg = "Files uploaded successfully";
        if (ServletFileUpload.isMultipartContent(request)) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            try {
                List<FileItem> items =  fileUpload.parseRequest(request);
                if (items != null) {
                    Iterator<FileItem> iter = items.iterator();
                    /*
                     * Return true if the instance represents a simple form
                     * field. Return false if it represents an uploaded file.
                     */
                    while (iter.hasNext()) {
                        final FileItem item = iter.next();
                        String itemName = item.getName();
                        final String fieldName = item.getFieldName();
                        final String fieldValue = item.getString();
                        if (item.isFormField()) {
                            name = fieldValue;
                            System.out.println("Field Name: " + fieldName + ", Field Value: " + item.getString("UTF-8").trim());
                            System.out.println("Candidate Name: " + name);
                            switch(fieldName){
                            case "description":
                            	post.setText(item.getString("UTF-8").trim());
                        
                            	break;
                            case "author":
                            	user.setUser_id(Long.valueOf(fieldValue));
                        		post.setAuthor(user);
                            	break;
                            case "head_count":
                            	post.setSmiles(Long.valueOf(fieldValue));
                            	break;
                            case "teams":
                            	String[] temp=fieldValue.split(",");
                            	for(String team:temp){
                            		team_ids.add(Long.valueOf(team));
                            	}
                            	break;
                            		
                            	
                            }
                        } else {
                            Image image=new Image();
                            image.setFilename("img_"+new Date().getTime()+".jpg");
                            
                        	itemName=image.getFilename();
                            final File file = new File(FILE_UPLOAD_PATH    + File.separator + itemName);
                          
                            File dir = file.getParentFile();
                            if(!dir.exists()) {
                                dir.mkdir();
                            }
                            
                            if(!file.exists()) {
                                file.createNewFile();
                            }
                            System.out.println("Saving the file: " + file.getName());
                    
                            
                          Long image_id=  ImageBuilder.addImage(image);
                          image.setImage_id(image_id);
                          System.out.println(image_id);
                          if(image_id!=null)images.add(image);
              
                            	FileOutputStream outputStream = new FileOutputStream(file);
                                 
                                // reads input image from file
                           BufferedImage inputImage = ImageIO.read(item.getInputStream());
                           inputImage = Scalr.resize(inputImage, 500, 300);
                                // writes to the output image in specified format
                                boolean result = ImageIO.write(inputImage, "JPG", outputStream);
                               
                                 
                        
                            
                                outputStream.close();
                               
                          
                        }
                    }
                }
            } catch (FileUploadException e) {
                code = 404;
                msg = e.getMessage();
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
                code = 404;
                msg = e.getMessage();
            }
        }
    	
        post.setImages(images);
		
        return PostBuilder.addTeamPost(post,team_ids);
    }
	
	
}

