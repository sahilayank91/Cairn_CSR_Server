package com.cairnindia.csr.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.imgscalr.Scalr;
import org.json.JSONException;
import org.json.JSONObject;

import com.cairnindia.csr.builder.AuthenticationBuilder;
import com.cairnindia.csr.builder.CommentBuilder;
import com.cairnindia.csr.builder.UserBuilder;
import com.cairnindia.csr.model.Comment;
import com.cairnindia.csr.model.Image;
import com.cairnindia.csr.model.User;
import com.cairnindia.csr.model.UserProfile;
import com.cairnindia.csr.utilities.Hashing;
import com.cairnindia.csr.utilities.Hashing.HashingResult;

@Path("/AuthenticationService")
public class AuthenticationService {
	private static final String PROFILE_UPLOAD_PATH = "/home/cairn/cairn_csr/profile_pictures";
	
	@POST //GET or post
	@Path("/Login") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public User authenticate(@FormParam("user_email")String user_email,@FormParam("user_password")String user_password){
		User user=new User();
		user=AuthenticationBuilder.getUser(user_email);
		if(user==null){System.out.println("Invalid email"); return null;}
		else{
			boolean authentic=Hashing.validatePassword(user_password,user.getHash(), user.getSalt());
			if(authentic==true) {System.out.println("success");}
			else{ System.out.println("invalid password");user=null;}
			return user;
		}
	}
	
	@POST //GET or post
	@Path("/updatePassword") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public User updatePassword(@FormParam("user_email")String user_email,@FormParam("old_password")String old_password,@FormParam("new_password")String new_password){
		User user=new User();
		user=AuthenticationBuilder.getUser(user_email);
		if(user==null){System.out.println("Invalid email"); return null;}
		else{
			boolean authentic=Hashing.validatePassword(old_password,user.getHash(), user.getSalt());
			if(authentic==true) {
				System.out.println("Authenticated");
			
				HashingResult hr = Hashing.createHash(new_password);
				user.setHash(hr.getHash());
				user.setSalt(hr.getSalt());
				
			
				AuthenticationBuilder.updatePassword(user);
				return user;

			}
			else{ System.out.println("invalid password");
			user=null;}
			return user;
		}
	}

	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public User register(@FormParam("user_name") String user_name,@FormParam("user_email") String user_email,
			@FormParam("user_phone") String user_phone
			,@FormParam("user_password") String user_password){
		HashingResult result = Hashing.createHash(user_password);
		String hash=result.getHash();
		String salt=result.getSalt();
		User user=new User();
		user.setSalt(salt);
		user.setHash(hash);
		user.setEmail(user_email);
		user.setPhone(user_phone);
		user.setName(user_name);
		UserBuilder.addUser(user);
		return user;
	}

	@POST
	@Path("/UpdateUserProfile/{department}/{summary}/{user_id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public void updateProfile(@FormParam("department") String department,@FormParam("summary") String summary,
			@FormParam("user_id") Long user_id){
		UserProfile profile=new UserProfile();
		profile.setDepartment(department);
		profile.setSummary(summary);
		profile.setUser_id(user_id);
		UserBuilder.updateUserProfile(profile);
	}

	@GET
	@Path("/RetrieveUserProfile/{user_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public UserProfile getUserProfile(@PathParam("user_id") Long user_id){
		UserProfile profile = UserBuilder.getUserProfile(user_id);
		return profile;
	}
	@POST
	@Path("/updateProfilePic")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public void updateProfilePic(@Context HttpServletRequest request)
	{
		Long user_id = null;



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
							case "user_id":
								user_id=Long.valueOf(fieldValue);

								break;


							}
						}
					}
					iter = items.iterator();

					while (iter.hasNext()) {
						final FileItem item = iter.next();
						String itemName = item.getName();
						final String fieldName = item.getFieldName();
						final String fieldValue = item.getString();
						if (!item.isFormField()) {

							final File file = new File(PROFILE_UPLOAD_PATH + File.separator + 
									user_id+".png");

							File dir = file.getParentFile();
							if(!dir.exists()) {
								dir.mkdir();
							}

							if(!file.exists()) {
								file.createNewFile();
							}
							System.out.println("Saving the file: " + file.getName());




							FileOutputStream outputStream = new FileOutputStream(file);

							// reads input image from file
							BufferedImage inputImage = ImageIO.read(item.getInputStream());
							inputImage = Scalr.resize(inputImage, 500, 300);
							// writes to the output image in specified format
							boolean result = ImageIO.write(inputImage, "PNG", outputStream);




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


		return;
	}
}
