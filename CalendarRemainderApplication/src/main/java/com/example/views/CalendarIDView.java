package com.example.views;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.example.controller.PermissionController;
import com.example.model.Event;
import com.example.model.User;
import com.example.services.CalendarManager;
import java.text.DateFormatSymbols;

public class CalendarIDView extends CalendarView{

	@Autowired
	PermissionController pc;
	
	private String calendarID;
	
	public List<Event> eventList;
	public List<User> allowedUserList;
	
	DateFormatSymbols dfsym = new DateFormatSymbols();
	
	public CalendarIDView(String calendarID) throws SQLException{
		super();
		this.calendarID = calendarID;
		//load the eventList
		calManager = new CalendarManager();
		eventList = calManager.getAllEvents(calendarID);
		
	}
	
	public String getCalendarID() {
		return calendarID;
	}
	
	
	public ModelAndView displayCalendar()
	{
		String[][] arr =  buildCalendar(year, month);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("calendarView.jsp");
		mv.addObject("obj", arr);
		mv.addObject("userId", calendarID);
		mv.addObject("year", this.year);
		mv.addObject("month",months[this.month]);
		
		int i = calendarID.indexOf("@");
		
		mv.addObject("username", calendarID.substring(0, i));
		return mv;
	}
	
	
	 public ModelAndView handleNext()
		{
			this.month = this.month + 1;
			if (month > 11)
			{
				this.month = 0;
				this.year = this.year + 1;
			}
			
			String[][] arr = buildCalendar(this.year, this.month);
			
			ModelAndView mv = new ModelAndView();
			mv.setViewName("calendarView.jsp");
			mv.addObject("obj", arr);
			mv.addObject("userId",this.calendarID);
			mv.addObject("year", this.year);
			mv.addObject("month",dfsym.getMonths()[this.month]);
			return mv;
			
		}
		
		public ModelAndView handlePrev()
		{
			this.month = this.month - 1;
			if (month < 0)
			{
				this.month = 11;
				this.year = this.year - 1;
			}
			
			String[][] arr = buildCalendar(this.year, this.month);
			//ModelAndView mv = displayCalendar(arr);
			ModelAndView mv = new ModelAndView();
			mv.setViewName("calendarView.jsp");
			mv.addObject("obj", arr);
			mv.addObject("userId",this.calendarID);
			mv.addObject("year", this.year);
			mv.addObject("month",dfsym.getMonths()[this.month]);
			return mv;
		}
	  
	  //for trail
		public void updateEvent(String calID,String date,String stime,String etime,String desc,String toMail) throws SQLException {
			
			calManager.updateEvent(calID, date, stime,etime,desc, toMail);
		}
		public List<Event> getAllEvents(String calID) throws SQLException{
			return calManager.getAllEvents(calID);
		}
		public Event getEvent(String CalId,Date date,String time) throws SQLException{
			return calManager.getEvent(CalId, date, time);
		}
	
		public boolean addEvent(String calID,String EventDescription,Date date,String startTime,String endTime,String ToMailId) throws SQLException{
			boolean ans = calManager.addEvent(calID, EventDescription, date, startTime,endTime,ToMailId);
			return ans;
		}
		
		public void deleteEvent(String calID,Date date,String Time) throws SQLException {
			calManager.deleteEvent(calID, date, Time);
		}
		
		public void displayOtherCalenderByID() {  
			//to be implemented later. (will implement later)
		}
}
