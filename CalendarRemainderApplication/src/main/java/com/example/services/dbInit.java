package com.example.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

public class dbInit {

	DataSource datasource = dbConnection.dataSource;
	Connection con;
	
	public dbInit() throws SQLException
	{
		con = datasource.getConnection();
	}
	
	
	public void buildTables() throws SQLException
	{
		buildUserTable();
		createEventTable();
	}
	
	private void buildUserTable() throws SQLException
	{
		String query="CREATE TABLE if not exists User (username VARCHAR(50) NOT NULL,password VARCHAR(50) NOT NULL,Email VARCHAR(50) NOT NULL,phonenumber VARCHAR(20) NOT NULL,PRIMARY KEY (username));" ;
		Statement stmt1 = con.createStatement();
		stmt1.execute(query);
	}
	
	private void createEventTable() throws SQLException {
		//function to create table in db.
		//create event table. --> (calendarID,Date,Time,Event Description,ToMailID)
		System.out.println("ok");
		String query = "create table if not exists CalendarEvents(calendarID varchar(50) not null,date varchar(15) not null,eventStartTime varchar(20) not null,eventEndTime varchar(20) not null,EventDescription varchar(400) not null,ToMailID varchar(40) not null,primary key(calendarID,date,TimeOfEvent));";
		Statement stmt = con.createStatement();
		stmt.execute(query);
	}
	
	
}
