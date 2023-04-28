package com.example.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.model.Event;
import com.example.model.User;
import com.example.views.CalendarIDView;
import com.example.views.Date;
import com.example.views.Login;
import com.example.services.ActiveUserViews;
import com.example.services.CalendarManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.text.DateFormat;  
import java.text.SimpleDateFormat;  

@RestController
public class ViewUpdateController {

	CalendarIDView c;
	
	@Value("${spring.mail.host}")
    private String mailHost;
	
	
	@Autowired
	Login l;
	
	@Autowired
	PermissionController pc;
	
	@Autowired
	ActiveUserViews cidStore;
	
	@RequestMapping("Register")
	public ModelAndView handleHome()
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("Register.jsp");
		return mv;
	}
	
	@RequestMapping("newUser")
	public String RegisterHandler(HttpServletRequest request) throws SQLException
	{
		String username = request.getParameter("username");
		String phonenumber = request.getParameter("phonenumber");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String repassword = request.getParameter("re_password");
		
		boolean check_uname = pc.checkPermission(username);
		if (check_uname == true)
		{
			if (password.equals(repassword))
			{
				pc.addUser(new User(username,password,phonenumber,email));
				return "Account created successfully";
			}
			else
			{
				return "password did not match";
			}
		}
		else
		{
			return "username already exists";
		}
		
	}
	
	
	@GetMapping("Login")
	public ModelAndView LoginHandler()
	{
		ModelAndView mv = l.DisplayLoginForm();
		return mv;
	}
	
	@RequestMapping("handleLogin")
	public ModelAndView permissionHandle(HttpServletRequest request,HttpServletResponse response) throws SQLException
	{
		ModelAndView mv = new ModelAndView();
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String calID = username+"@CALID";
		boolean permission =  pc.checkPermission(username, password);
		if(permission == false)
		{
			mv = l.DisplayLoginForm();
		}	
		else
		{
			c = new CalendarIDView(calID);
			cidStore.addUserView(c);
	  		mv = c.displayCalendar();
			cidStore.getUserView(calID).displayCalendar();
			mv = c.displayCalendar();
		}
		return mv;
		
		
	}
	/* to be modified  */
	@RequestMapping(value = "/updateView", method = RequestMethod.POST, params = "next")
	public ModelAndView Next(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String calID = request.getParameter("userId");
		//fetch cidsStore[username] and assign to c
		c = cidStore.getUserView(calID);
		//cidStore
		ModelAndView mv = c.handleNext();
		return mv;
	}
		
	@RequestMapping(value = "/updateView", method = RequestMethod.POST, params = "prev")
	public ModelAndView Prev(HttpServletRequest request) {
		String calID = request.getParameter("userId");
		//fetch cidsStore[username] and assign to c
		c = cidStore.getUserView(calID);
		//cidStore
		ModelAndView mv = c.handlePrev();
		return mv;
	}
	
	@RequestMapping(value = "/eventForm", method = RequestMethod.POST, params = "Add Event")
	public String AddEvent(HttpServletRequest request)
	{
		String[] nums = request.getParameter("curPage").split("-");
		String year = nums[1];
		String month = nums[0];
		
		return "Add Event";
	}

	
	@RequestMapping("/eventTable/{userId}/{date}")
	public ModelAndView renderEventTable(@PathVariable String userId,@PathVariable String date)
	{
		c = cidStore.getUserView(userId);
		int day = Integer.parseInt(date.split("-")[2]);
		
		int month = c.month + 1;
		int year = c.year;
		
		Date d = new Date(year,month,day);
		System.out.println(d.getCurrDate());
		List<Event> el =  c.getDaySchedules(userId, d);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("forward:/Event.jsp");
		mv.addObject("userId", userId);
		mv.addObject("date", d.getCurrDate());
		mv.addObject("EventList", el);
		
		System.out.println(el);
		
		return mv;
		
		
	}
	
	@RequestMapping("eventPage")
	public RedirectView EventPage(HttpServletRequest request) 
	{
		String calId = request.getParameter("userId");
		String date = request.getParameter("date");
		
		int day = Integer.parseInt(date.split("/")[0]);
		
		c = cidStore.getUserView(calId);
		int month = c.month + 1;
		int year = c.year;

		Date d = new Date(year,month,day);
		return new RedirectView("/eventTable/"+calId + "/" + d.getCurrDate());
		
	}
	

 	//i have to change here
	@RequestMapping("/updateEvent/update/{userId}/{date}/{stime}/{etime}")
	public ModelAndView updateForm(HttpServletRequest request,@PathVariable String userId,@PathVariable String date,@PathVariable String stime,@PathVariable String etime) throws SQLException
	{
		c = cidStore.getUserView(userId);

		String[] nums = date.split("-");
		
		System.out.println(stime);
		
		Date d = new Date(Integer.parseInt(nums[0]),Integer.parseInt(nums[1]),Integer.parseInt(nums[2]));
		
		Event event = c.getEvent(userId,d,stime);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("forward:/updateForm.jsp");
		mv.addObject("userId",userId);
		mv.addObject("date",date);
		mv.addObject("eventStartTime",stime);
		mv.addObject("eventEndTime",etime);
		mv.addObject("toMail", event.getToEmailAddr());
		mv.addObject("eventDesc", event.getEventDescription());
		
		return mv;
	}
	
	//i have to change here
	@RequestMapping("/updateToDb")
	public RedirectView UpdateToDb(HttpServletRequest request) throws SQLException
	{
		String userId = request.getParameter("userId");
		String date = request.getParameter("date");
		String esTime = request.getParameter("eventStartTime");
		String eeTime = request.getParameter("eventEndTime");
		String eventDesc = request.getParameter("eventDesc");
		String toMail = request.getParameter("toMail");
		
		c = cidStore.getUserView(userId);
		c.updateEvent(userId, date, esTime,eeTime,eventDesc, toMail);
		return new RedirectView("/eventTable/"+userId + "/" + date);
	}
	
	@RequestMapping("/updateEvent/Add/{userId}/{date}")
	public ModelAndView addEventForm(@PathVariable String userId,@PathVariable String date)
	{
		
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("forward:/AddDetails.jsp");
		mv.addObject("userId",userId);
		mv.addObject("date", date);
		
		return mv;
	}
	
	@RequestMapping("/updateEvent/delete/{userId}/{date}/{time}")
	public RedirectView handleDelete(HttpServletRequest request,@PathVariable String userId,@PathVariable String date,@PathVariable String time) throws SQLException
	{
		c = cidStore.getUserView(userId);
		String[] nums = date.split("-");
		
		Date d = new Date(Integer.parseInt(nums[0]),Integer.parseInt(nums[1]),Integer.parseInt(nums[2]));
		c.deleteEvent(userId,d, time);
		//return "Deleted successfully";
		return new RedirectView("/eventTable/"+userId+"/" + date);
	}
	
	//changed here
	@RequestMapping(value = "/eventRegister",method = RequestMethod.POST)
	public RedirectView handleRegister(HttpServletRequest request) throws SQLException
	{
		String userId = request.getParameter("userId");
		String date = request.getParameter("date");
		String stime = request.getParameter("stime");
		String etime = request.getParameter("etime");
		String eventDesc = request.getParameter("eventDes");
		String Tomail = request.getParameter("toMail");
		
		c = cidStore.getUserView(userId);
		
		int year = c.year;
		int month = c.month + 1;
		int day = Integer.parseInt(date.split("-")[2]);
		
		boolean res = c.addEvent(userId, eventDesc, new Date(year,month,day), stime,etime ,Tomail);
		if(res == false )
		{
			System.out.println("collision");
			
		}
		
		return new RedirectView("/eventTable/"+userId + "/" + date);
	}
	@RequestMapping("/goToCal/{userId}")
	public ModelAndView dispCal(@PathVariable String userId)
	{
		c = cidStore.getUserView(userId);
		ModelAndView mv = c.displayCalendar();
		mv.setViewName("forward:/calendarView.jsp");
		return mv;
	}
	
	@RequestMapping("/get/allEvents/{userId}")
	public ModelAndView handleGetAll(@PathVariable String userId) throws SQLException
	{
		System.out.println("I am in " + userId);
		c = cidStore.getUserView(userId);
		List<Event> el = c.getAllEvents(userId);
		
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("userId",userId);
		mv.addObject("EventList", el);
		mv.setViewName("forward:/Event.jsp");
		
		return mv;
	}
	
}