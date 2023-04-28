package com.example.controller;

import java.sql.SQLException;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.EmailDetails;
import com.example.model.Event;
import com.example.services.CalendarManager;
import com.example.services.EmailService;
import com.example.services.EmailServiceImpl;
import com.example.views.Date;

import jakarta.mail.MessagingException;

import java.util.Timer;
import java.util.TimerTask;


@RestController
public class EventController {
	

	CalendarManager cManager;
	
	public Event buildEvent(String des,String startTime,String endTime,Date date,String mailId) throws SQLException {
		Event e =  new Event(des,startTime,endTime,date.getCurrDate(),mailId);
		cManager = new CalendarManager();
		return e;
	}
	
	
	
	
	public void Schedule(String calID,Event e) {
		/*
		 
		 1.Split the time into hh:mm:ss , date into yyyy,mm,dd.
		 2.set the hh,mm,ss and yyyy,mm,dd to cal instance.
		 3.substract 15-20 sec from time using get,set in cal instance.
		 4.set timer to trigger at cal.getTime.
		 5.In Timer, db check at dest time should happen.
		 6.if all fine send mail. 
		 
		 */
		
		String[] tt = e.getstartTime().split(":");
		String[] td = e.getDate().split("-");
		
		int year = Integer.parseInt(td[0]);
		int month = Integer.parseInt(td[1]) - 1;
		int day = Integer.parseInt(td[2]);
		
		System.out.println(day + "-" + month + "-" + year);
		
		int hour = Integer.parseInt(tt[0]);
		int minute = Integer.parseInt(tt[1]);
		int second = 0; //Integer.parseInt(tt[2]);
		
		System.out.println(hour +  ":" + minute + ":" + second);
		
		Calendar cal = Calendar.getInstance();
		cal.set(year,month,day,hour,minute,second);
		
		System.out.println(cal.getTime());
		
		Timer timer = new Timer();
		TimerTask task = new EventTimer(e,timer,calID,cManager);
		timer.schedule(task, cal.getTime());
				
	}

}


class EventTimer extends TimerTask
{

	Event e;
	Timer t;
	String calID;
	CalendarManager cManager;
	
	private EmailServiceImpl emailService = new EmailServiceImpl();
	
	public EventTimer(Event e,Timer t,String calID,CalendarManager cManager)
	{
		this.e = e;
		this.t = t;
		this.calID = calID;
		this.cManager = cManager;
	}
	
	@Override
	public void run() {
		
		String[] nums = e.getDate().split("-");  
		Date d = new Date(Integer.parseInt(nums[0]),Integer.parseInt(nums[1]),Integer.parseInt(nums[2]));
		
		try {
			System.out.println("in run");
			if (cManager.getEvent(calID,d,e.getstartTime()) == null)
			{
				System.out.println("Event got deleted");
				t.cancel();
			}
			else
			{
				System.out.println("finally");
				EmailDetails ed = new EmailDetails(e.getToEmailAddr(),e.getEventDescription(),"Your Remainder");
				emailService.sendMail(ed);
				t.cancel();
			}
		} catch (SQLException e1) {
			System.out.println("Exception");
			e1.printStackTrace();
		} catch (MessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
	
}




