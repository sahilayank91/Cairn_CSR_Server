package com.cairnindia.csr.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.imgscalr.Scalr;

import com.cairnindia.csr.builder.ImageBuilder;
import com.cairnindia.csr.builder.NandgramBuilder;
import com.cairnindia.csr.builder.PostBuilder;
import com.cairnindia.csr.model.DayAttendance;
import com.cairnindia.csr.model.Image;
import com.cairnindia.csr.model.Nandgram;
import com.cairnindia.csr.model.NandgramActivity;
import com.cairnindia.csr.model.NandgramAttendance;
import com.cairnindia.csr.model.NandgramLocations;
import com.cairnindia.csr.model.Post;
import com.cairnindia.csr.model.Statistics;
import com.cairnindia.csr.model.User;


@Path("/NandgramService")
public class NandgramService {

	private static final String FILE_UPLOAD_PATH = "/home/cairn/cairn_csr/images";
	@GET
	@Path("/getNandgramList/{address_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Nandgram> getNandgramList(@PathParam("address_id")Long address_id){
		ArrayList<Nandgram>  nandgram_list= NandgramBuilder.getNandgramList(address_id);
		return nandgram_list;
	}
	
	
	@GET
	@Path("/getNandgramAddress")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<NandgramLocations> getNandgramAddress() throws SQLException{
		ArrayList<NandgramLocations>  nandgram_list= NandgramBuilder.getNandgramAddress();
		return nandgram_list;
	}
	
	
	@GET
	@Path("/getNandgramDayAttendance/{date}/{nandgram_id}/{date}")
	@Produces(MediaType.APPLICATION_JSON)
	public NandgramAttendance getNandgramDayAttendance(@PathParam("date")java.sql.Date date,@PathParam("nandgram_id")int nandgram_id,@PathParam("slot")int slot){
		try {
			return NandgramBuilder.getDayAttendance(date,nandgram_id,slot);	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	@GET
	@Path("getNandgramAttendance/{nandgram_id}/{date}")
	@Produces(MediaType.APPLICATION_JSON)
	
	public DayAttendance getNandgramAttendance(@PathParam("nandgram_id")Long id,@PathParam("date")java.sql.Date date) throws SQLException{
		DayAttendance dayattendance = new DayAttendance();
		dayattendance = NandgramBuilder.getNandgramAttendance(date,id);
		return dayattendance;
		
	}
	@GET
	@Path("/getActivity/{nandgram_id}/{date}")
	@Produces(MediaType.APPLICATION_JSON)
	
	public ArrayList<NandgramActivity> getNandgramActivity(@PathParam("nandgram_id")Long id,@PathParam("date")java.sql.Date date) throws SQLException{
		ArrayList<NandgramActivity> nandgramActivity = new ArrayList<NandgramActivity>();
		nandgramActivity = NandgramBuilder.getNandgramActivity(id,date);
		return nandgramActivity;
		
		
	}
	
	@GET
	@Path("/getActivityviaAddress/{address_id}/{date}")
	@Produces(MediaType.APPLICATION_JSON)
	
	public ArrayList<NandgramActivity> getNandgramActivityviaAddress(@PathParam("address_id")Long id,@PathParam("date")java.sql.Date date) throws SQLException{
		ArrayList<NandgramActivity> nandgramActivity = new ArrayList<NandgramActivity>();
		nandgramActivity = NandgramBuilder.getNandgramActivityviaAddress(id,date);
		return nandgramActivity;
		
		
	}
	

	@POST
	@Path("/addAttendance")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)

	public NandgramAttendance addNandgramAttendance(@Context HttpServletRequest request)
	{

		NandgramAttendance attendance=new NandgramAttendance();
		User user=new User();
		Image image=new Image();

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


							case "longitude":
								attendance.setLongitude(Double.valueOf(fieldValue));
								break;
							case "latitude":
								attendance.setLatitude(Double.valueOf(fieldValue));
								break;
							case "head_count":
								attendance.setHead_count(Integer.valueOf(fieldValue));
								break;
							case "nandgram_id":
								attendance.setNandgram_id(Long.valueOf(fieldValue));
								break;
							case "user_id":
								attendance.setUser_id(Long.valueOf(fieldValue));
								break;
							case "slot":
								attendance.setSlot(Integer.valueOf(fieldValue));
								break;
							case "att_num":
								attendance.setAtt_Num(Long.valueOf(fieldValue));
								break;
							}
						} else {

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
							if(image_id!=null)     image.setImage_id(image_id);
							else return null;
							System.out.println(image_id);


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

		attendance.setImage_id(image.getImage_id());
		attendance.setAttendance_id(NandgramBuilder.addNandgramAttendance(attendance));
		return attendance;
	}
	@GET
	@Path("/getNandgramWeekAttendance/{nandgram_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<DayAttendance> getNandgramWeekAttendance(@PathParam("nandgram_id")Long id){
		Date start_date,end_date;
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);

		end_date=cal.getTime();
		cal.add(Calendar.DATE, -7);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		start_date=cal.getTime();

		ArrayList<NandgramAttendance> list= NandgramBuilder.getRangeAttendance(start_date, end_date,id);
		ArrayList<DayAttendance> attendance_list=new ArrayList<>();
		
		Calendar current=Calendar.getInstance();
		current.setTime(new Date(start_date.getTime()));
		
		for(int i=0;i<7;i++){
			DayAttendance attendance=new DayAttendance();
			attendance.setTime(current.getTimeInMillis());
			current.add(Calendar.DATE, 1);

			for(int l=0;l<list.size();l++){
				Calendar temp=Calendar.getInstance();
				temp.setTime(new Date(list.get(l).getTime()));
				temp.set(Calendar.HOUR_OF_DAY, 0);
				temp.set(Calendar.MINUTE, 0);
				temp.set(Calendar.SECOND, 0);
				temp.set(Calendar.MILLISECOND, 0);
				if(current.getTimeInMillis()==temp.getTimeInMillis()){
					if(list.get(l).getSlot()==1)
						attendance.setHead_count_slot1(list.get(l).getHead_count());
					if(list.get(l).getSlot()==2)
						attendance.setHead_count_slot2(list.get(l).getHead_count());

				}
			}
			attendance_list.add(attendance);
		}

		return attendance_list;
	}
	
	
	
	@POST
	@Path("/addActivity")
	@Consumes(MediaType.MULTIPART_FORM_DATA+ "; charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON)

	public NandgramActivity addActivity(@Context HttpServletRequest request)
	{
		
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		NandgramActivity nandgramActivity = new NandgramActivity();
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
                            	nandgramActivity.setText(item.getString("UTF-8").trim());
                        
                            	break;
                            case "author":
                            	user.setUser_id(Long.valueOf(fieldValue));
                            	nandgramActivity.setAuthor(user);
                            	break;
                            case "head_count":
                            	nandgramActivity.setHeadCount(Long.valueOf(fieldValue));
                               	break;
                            case "activity":
                            	nandgramActivity.setActivity(fieldValue);
                            	break;
                            case "nandgram_id":
                            	nandgramActivity.setNandgramId(Long.valueOf(fieldValue));
                            	break;
                            case "address_id":
                            	nandgramActivity.setAddressId(Long.valueOf(fieldValue));
                            	break;
                            case "nandgram_name":
                            	nandgramActivity.setNandgramName(fieldValue);
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
    	
        nandgramActivity.setImages(images);
		
        return NandgramBuilder.addActivity(nandgramActivity);
    }
	
	
	
	
	
}


	

