package com.example.services;

import java.util.List;

import com.example.model.Event;
import com.example.views.Date;

public interface ISchedule {
	
	List<Event> getData(String calID);
	public List<Event> getDaySchedules(String calID,Date date);

}
