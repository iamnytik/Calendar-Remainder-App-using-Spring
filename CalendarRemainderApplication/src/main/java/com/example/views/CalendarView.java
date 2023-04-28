package com.example.views;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;

import com.example.model.Event;
import com.example.services.CalendarManager;
import com.example.services.ISchedule;

public class CalendarView implements ISchedule{
	protected CalendarManager calManager;
	String[] months = { "January", "February", "March", "April", "May", "June", "July", "August","September", "October", "November", "December" };
	int[] numDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	
	public int year = Integer.parseInt(Year.now().toString());
	public int month = LocalDate.now().getMonthValue() - 1; 	
	
	@Override
	public List<Event> getData(String calID) {
		List<Event> res; 
		try {
			res = calManager.getAllEvents(calID);
		} catch (SQLException e) {
			res = null;
			e.printStackTrace();
		}
		return res;
	}
	
	@Override
	public List<Event> getDaySchedules(String calID,Date date) {
		List<Event> res; 
		try {
			res =  calManager.getDayEvents(calID, date);
		} catch (SQLException e) {
			e.printStackTrace();
			res = null;
		}
		return res;
	}

	
	
	public boolean isLeapYear(int year) 
	{
		boolean ans = false;
	    if (year % 4 == 0) {
	    	 ans = true;
	    }
	     
	    return ans;
	}

	  public int daysInMonth(int year, int month) 
	  {
	    int days = numDays[month];
	    if (month == 1 && isLeapYear(year))
	    {
	    	++days;
	    }
	      
	    return days;
	  }
	  
	  public String[][] buildCalendar(int year,int month)
	  {
		    String[][] arr = new String[7][7];
			for (int i = 0;i < 7;++i)
			{
				for(int j = 0;j < 7;++j)
				{
					arr[i][j] = " ";
				}
			}
			
			java.util.GregorianCalendar cal = new java.util.GregorianCalendar();
		    cal.set(year, month, 1);
		    int offset = cal.get(java.util.GregorianCalendar.DAY_OF_WEEK) - 1;
		    offset += 7;
		    int num = daysInMonth(year, month);
		    for (int i = 0; i < num; ++i) {
		      arr[offset / 7][offset % 7] = Integer.toString(i + 1);
		      ++offset;
		    }
		  
		  return arr;
	  }
	
}