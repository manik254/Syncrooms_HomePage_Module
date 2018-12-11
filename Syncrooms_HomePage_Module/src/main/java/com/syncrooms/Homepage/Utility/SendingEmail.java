package com.syncrooms.Homepage.Utility;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class SendingEmail {
	 public static Email email=null;
	
	
	public static void sendEmail() throws EmailException {
		
		email=new SimpleEmail();
		System.out.println("Email sending is started");
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("koppulamanikyalarao@gmail.com", "koppula254"));
		email.setSSLOnConnect(true);
		email.setFrom("koppulamanikyalarao@gmail.com");
		email.setSubject("Selenium Test Report");
		email.setMsg("this is a test mail from selenium");
		email.addTo("koppulamanikyalarao@gmail.com");
		email.send();
		System.out.println("email sended to corresponding mail id");
		
	}
}
