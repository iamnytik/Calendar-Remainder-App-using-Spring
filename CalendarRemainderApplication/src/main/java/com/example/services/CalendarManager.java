package com.example.services;
import java.util.*;

import javax.sql.DataSource;

import com.example.controller.EventController;
import com.example.views.Date;
import com.example.model.Event;
import java.time.LocalTime;
import java.sql.*;


public class CalendarManager {

	 DataSource dataSource = null;
     Connection con = null;
	 EventController ec = new EventController();
	
	public CalendarManager()throws SQLException{
		dataSource=dbConnection.dataSource;     //ConfigureDataSource.source();
		con = dataSource.getConnection();
//		createDBTable();
	}
	
	public boolean addEvent(String calID,String EventDescription,Date date,String startTime,String endTime,String ToMailId) throws SQLException {
		//add event to table
		//String squery = "select * from CalendarEvents where calendarID=" +  '"' +calID + '"' +  " and date=" + '"' +date.getCurrDate() + '"' + " and TimeOfEvent=" + '"' + Time + '"' + ";";
		String iquery = "insert into CalendarEvents values(" + '"' +calID + '"' + "," + '"' + date.getCurrDate()+ '"' + "," + '"' + startTime + '"' +"," + '"' + endTime + '"' + ',' + '"' + EventDescription + '"' + ", " + '"' + ToMailId + '"' +");";
		
		
		Statement sstmt = con.createStatement();
		
		
		List<Event> el = getDayEvents(calID, date);
		
		int count = 0;
		
		
		LocalTime qsTime = LocalTime.parse(startTime);
		LocalTime qeTime = LocalTime.parse(endTime);
		
		//to be done
		for(Event i : el)
		{
			String st = i.getstartTime();
			String et = i.getendTime();
			
			LocalTime sTime = LocalTime.parse(st);
			LocalTime eTime = LocalTime.parse(et);
			
			if(qsTime.isAfter(sTime) && qsTime.isBefore(eTime))
			{
				++count;
			}
			if (qeTime.isAfter(sTime) && qeTime.isBefore(eTime))
			{
				++count;
			}
			if (qsTime.equals(sTime))
			{
				++count;
			}
	
		}
		boolean ans= true;
		if (count > 0)
		{
			System.out.println("There is a collision");
			return false;
		}
		else if (count == 0)
		{
			sstmt.executeUpdate(iquery);
			ec.Schedule(calID, ec.buildEvent(EventDescription, startTime, endTime, date, ToMailId));
			//return true;
		}
		return ans;
		
		
	}
	
	/* have to add few more agrs */
	public void updateEvent(String calID,String date,String stime,String etime,String desc,String toMail) throws SQLException {
		String sql = "update CalendarEvents set eventStartTime=?,eventEndTime=?,EventDescription=?,ToMailID=? where calendarID=? and date=?;";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, stime);
		stmt.setString(2,etime);
		stmt.setString(3, desc);
		stmt.setString(4, toMail);
		stmt.setString(5, calID);
		stmt.setString(6, date);
		
		stmt.executeUpdate();
		
	}
	
	public void deleteEvent(String calID,Date date,String Time) throws SQLException {
		String query = "delete from CalendarEvents where calendarID=" + '"' + calID + '"' + " and date=" + '"' + date.getCurrDate() + '"' +" and eventStartTime="+ '"' + Time + '"'  +";"; 
		PreparedStatement stmt = con.prepareStatement(query);

		stmt.executeUpdate();
	}
	
	public List<Event> getDayEvents(String calID,Date date) throws SQLException{
		//read
		//"select * from CalendarEvents where calendarID=" + calID + " and date=" + date.getCurrDate() + ";"
		String query ="SELECT * FROM CalendarEvents where calendarID=" + '"' + calID + '"' + " and date=" + '"' + date.getCurrDate() + '"' + ';';
		Statement stmt = con.createStatement();
		ResultSet res =  stmt.executeQuery(query);
		List<Event> eventList = new ArrayList<Event>();
		
		
		while(res.next()) {
			System.out.println("fetching...");
			eventList.add(ec.buildEvent(res.getString(5),res.getString(3),res.getString(4),date,res.getString(6)));
		}
		System.out.println(eventList);
		return  eventList;
	}
	
	public Event getEvent(String CalId,Date date,String time) throws SQLException
	{
		List<Event> EventList = new ArrayList<>();
		String sql = "select * from CalendarEvents  where calendarID=" + '"' + CalId + '"' + " and date="+ '"' + date.getCurrDate() + '"' + "and eventStartTime="+'"' + time + '"' + ';';
		Statement stmt = con.createStatement();
		
		ResultSet res = stmt.executeQuery(sql);
		
		if(res.next())
		{
			EventList.add(ec.buildEvent(res.getString(5),res.getString(3),res.getString(4),date,res.getString(6)));
		}
		
		if(EventList.size() == 0)
		{
			return null;
		}
		return EventList.get(0);
	}
	
	public List<Event> getAllEvents(String calID) throws SQLException{
		String query = "select * from CalendarEvents where calendarID=" + '"' + calID + '"' +  ";";
		Statement stmt = con.createStatement();
		ResultSet res =  stmt.executeQuery(query);
		List<Event> eventList = new ArrayList<Event>();
		
		while(res.next()) {
			String[] date = res.getString(2).split("-");
			Date d = new Date(Integer.parseInt(date[0]),Integer.parseInt(date[1]),Integer.parseInt(date[2]));
			eventList.add(ec.buildEvent(res.getString(5),res.getString(3),res.getString(4),d,res.getString(6)));
		}
		System.out.println(eventList);
		return  eventList;
	}
	
}