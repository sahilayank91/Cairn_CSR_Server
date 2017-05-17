package com.cairnindia.csr.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
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
import com.cairnindia.csr.model.DayAttendance;
import com.cairnindia.csr.model.Image;
import com.cairnindia.csr.model.Nandgram;
import com.cairnindia.csr.model.NandgramAttendance;
import com.cairnindia.csr.model.User;


@Path("/NandgramService")
public class NandgramService {

	private static final String FILE_UPLOAD_PATH = "/home/cairn/cairn_csr/nandgram";
	@GET
	@Path("/getNandgramList")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Nandgram> getNandgramList(){
		ArrayList<Nandgram> nandgram_list= NandgramBuilder.getNandgramList();
		return nandgram_list;
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
	@Path("/getNandgramWeekAttendance")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<DayAttendance> getNandgramWeekAttendance(){
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

		ArrayList<NandgramAttendance> list= NandgramBuilder.getRangeAttendance(start_date, end_date);
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


}
