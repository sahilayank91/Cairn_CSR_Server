package com.cairnindia.csr.utilities;



import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;



public class Mailer
{
  public static void main(String [] args)
  {    
	  sendHTMLEmail("sahilayank91@gmail.com","Reset Password","Hello! <h1>This works</h1>");
  }
  public static void sendPlainTextEmail(String receiver,String subject,String body ){
	   
	     // Recipient's email ID needs to be mentioned.
	     String to = receiver;

	     // Sender's email ID needs to be mentioned
	     String from = "Cairn CSR Android App";
	     final String username = "cairncsr.app@gmail.com";
	     final String password= "cairn_iiitk2017";

	     String host = "smtp.gmail.com";

	     // Get system properties
	     Properties properties = System.getProperties();

	     // Setup mail server
	     properties.setProperty("mail.smtp.host", host);
	     properties.setProperty("mail.smtp.port", "587");
	     properties.setProperty("mail.smtp.auth", "true");
	     properties.setProperty("mail.smtp.starttls.enable","true");

	     // Get the default Session object.
	     Session session = Session.getInstance(properties, new javax.mail.Authenticator(){
	    	 protected PasswordAuthentication getPasswordAuthentication(){
	    		 return new PasswordAuthentication(username,password);
	    	 }
	    	 
	    	 });

	     try{
	        // Create a default MimeMessage object.
	        MimeMessage message = new MimeMessage(session);

	        // Set From: header field of the header.
	        try {
				message.setFrom(new InternetAddress(username,from));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        // Set To: header field of the header.
	        message.addRecipients(Message.RecipientType.TO,  InternetAddress.parse(to));

	        // Set Subject: header field
	        message.setSubject(subject);

	        
	        // Create the message part 
	        BodyPart messageBodyPart = new MimeBodyPart();
	        
          // String random= utilities.Random.generateRandomString(20);
	        messageBodyPart.setText(body);
	        
	        // Create a multipart message
	        Multipart multipart = new MimeMultipart();

	        // Set text message part
	        multipart.addBodyPart(messageBodyPart);
	        message.setContent(multipart );
	       	        // Send message
	        Transport.send(message);
	        System.out.println("Sent message successfully....");
	     }catch (MessagingException mex) {
	        mex.printStackTrace();
	     }
	  
	  
  }
  public static void sendHTMLEmail(String receiver,String subject,String body ){
	   
	     // Recipient's email ID needs to be mentioned.
	     String to = receiver;

	     // Sender's email ID needs to be mentioned
	     String from = "Cairn CSR Android App";
	     final String username = "cairncsr.app@gmail.com";
	     final String password= "cairn_iiitk2017";
	     //String username ="2013kucp1024@iiitkota.ac.in";
	    // String password="iiitk1024";
	     // Assuming you are sending email from localhost
	     String host = "smtp.gmail.com";

	     // Get system properties
	     Properties properties = System.getProperties();

	     // Setup mail server
	     properties.setProperty("mail.smtp.host", host);
	     properties.setProperty("mail.smtp.port", "587");
	     properties.setProperty("mail.smtp.auth", "true");
	     properties.setProperty("mail.smtp.starttls.enable","true");

	     // Get the default Session object.
	     Session session = Session.getInstance(properties, new javax.mail.Authenticator(){
	    	 protected PasswordAuthentication getPasswordAuthentication(){
	    		 return new PasswordAuthentication(username,password);
	    	 }
	    	 
	    	 });

	     try{
	        // Create a default MimeMessage object.
	        MimeMessage message = new MimeMessage(session);

	        // Set From: header field of the header.
	        try {
				message.setFrom(new InternetAddress(username,from));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        // Set To: header field of the header.
	        message.addRecipients(Message.RecipientType.TO,  InternetAddress.parse(to));

	        // Set Subject: header field
	        message.setSubject(subject);

	        
	   
	        message.setContent(body, "text/html");
	       	        // Send message
	        Transport.send(message);
	        System.out.println("Sent message successfully....");
	     }catch (MessagingException mex) {
	        mex.printStackTrace();
	     }
	  
	  
}
}

