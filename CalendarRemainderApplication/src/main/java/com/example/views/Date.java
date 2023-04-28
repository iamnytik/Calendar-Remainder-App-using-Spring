package com.example.views;

public class Date {
	int day;
	int month;
	int year;
	
	
	public Date(int year,int month,int day)
	{
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	public String getCurrDate() {
		return year + "-" + month + "-" + day;
	}
}
