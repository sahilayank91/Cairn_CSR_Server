package com.cairnindia.csr.controller;


import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.spi.CalendarNameProvider;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
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
import com.cairnindia.csr.builder.StatisticsBuilder;
import com.cairnindia.csr.builder.TeamBuilder;
import com.cairnindia.csr.model.Comment;
import com.cairnindia.csr.model.Image;
import com.cairnindia.csr.model.Post;
import com.cairnindia.csr.model.Statistics;
import com.cairnindia.csr.model.Team;
import com.cairnindia.csr.model.User;

@Path("/StatisticsService")
public class StatisticsService {

	@GET
	@Path("/getUserDaySmiles/{userid}/{date}")
	@Produces(MediaType.APPLICATION_JSON)
	public Long getDaySmiles(@PathParam("userid") Long userid,@PathParam("date") java.sql.Date date){
		Long smiles=StatisticsBuilder.getDaySmiles(date,userid);
		return smiles;
	}
	
	@GET
	@Path("/getUserDaysSmiles/{userid}/{start_date}/{end_date}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Long> getWeekSmiles(@PathParam("userid") Long userid,@PathParam("start_date")  java.sql.Date start_date,@PathParam("end_date")  java.sql.Date end_date){
		ArrayList<Long> smiles=StatisticsBuilder.getRangeSmiles(start_date,end_date,userid);
		return smiles;
	}
	@GET
	@Path("/getUserWeekSmiles/{userid}/{month}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Long> getUserWeekSmiles(@PathParam("userid") Long userid,@PathParam("month") Long month){
		ArrayList<Long> smiles=StatisticsBuilder.getWeekSmiles(new Date(month),userid);
		return smiles;
	}

	@GET
	@Path("/getStatistics/{type}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Statistics getStatistics(@PathParam("type")String type,@PathParam("id")Long id){
		Statistics statistics=new Statistics();
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date end_date=cal.getTime();
				
		if(type.equals("user")){
			//Week
			Calendar temp=Calendar.getInstance();
			temp.add(Calendar.DATE, -6);
			Date start_date=temp.getTime();
			statistics.setWeek(StatisticsBuilder.getRangeSmiles(start_date, end_date, id));
			statistics.setWeek_dates(StatisticsBuilder.getDaywiseRangeDates(start_date, end_date));
			
			
			//Month
			statistics.setMonth(StatisticsBuilder.getWeekSmiles(start_date, id));
			statistics.setMonth_dates(StatisticsBuilder.getWeekwiseRangeDates(start_date));
//			//Year
			statistics.setYear(StatisticsBuilder.getMonthSmiles(end_date, id));
//			
			statistics.setYear_dates(StatisticsBuilder.getMonthwiseRangeDates(start_date));
			statistics.setObject_id(id);
			statistics.setTeam(false);
		}
		else{
			//Week
			Calendar temp=Calendar.getInstance();
			temp.add(Calendar.DATE, -6);
			Date start_date=temp.getTime();
			statistics.setWeek(StatisticsBuilder.getRangeTeamSmiles(start_date, end_date, id));
			statistics.setWeek_dates(StatisticsBuilder.getDaywiseRangeDates(start_date, end_date));
			
			
			//Month
			statistics.setMonth(StatisticsBuilder.getTeamWeekSmiles(start_date, id));
			statistics.setMonth_dates(StatisticsBuilder.getWeekwiseRangeDates(start_date));
//			//Year
			statistics.setYear(StatisticsBuilder.getTeamMonthSmiles(end_date, id));
//			
			statistics.setYear_dates(StatisticsBuilder.getMonthwiseRangeDates(start_date));
			statistics.setObject_id(id);
			statistics.setTeam(true);
		}
		
		return statistics;
	}

}

