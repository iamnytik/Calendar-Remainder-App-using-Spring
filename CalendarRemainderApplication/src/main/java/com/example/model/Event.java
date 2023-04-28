package com.example.model;

public class Event {
	private String EventDescription;
	private String startTime;
	private String endTime;
	private String date;
	private String ToEmailAddr;
	
	
	public Event(String des,String startTime,String endTime,String date,String ToEmailAddr){
			EventDescription = des;
			this.startTime = startTime;
			this.endTime = endTime;
			this.date = date;
			this.ToEmailAddr = ToEmailAddr;
	}
	
	
	
	
	public String getEventDescription() {
		return EventDescription;
	}


	public void setEventDescription(String eventDescription) {
		EventDescription = eventDescription;
	}

	
	public String getendTime()
	{
		return endTime;
	}
	
	public void setendTime(String time)
	{
		this.endTime = time;
	}
	
	public String getstartTime() {
		return startTime;
	}


	public void setstartTime(String time) {
		startTime = time;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getToEmailAddr() {
		return ToEmailAddr;
	}


	public void setToEmailAddr(String toEmailAddr) {
		ToEmailAddr = toEmailAddr;
	}


	
	
}
