package com.example.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.views.CalendarIDView;

@Component
public class ActiveUserViews {
	private Map<String, CalendarIDView> m = new HashMap<>();
	
	public void addUserView(CalendarIDView cal)
	{
		m.put(cal.getCalendarID(), cal);
	}
	
	public CalendarIDView getUserView(String calId)
	{
		return m.get(calId);
	}
}
