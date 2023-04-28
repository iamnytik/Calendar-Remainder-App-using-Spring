package com.example.demo;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.services.dbInit;

@SpringBootApplication(scanBasePackages = {"com.example.demo","com.example.controller","com.example.model","com.example.services","com.example.views"})
public class CalendarRemainderApplication {

	public static void main(String[] args) throws SQLException {
		  SpringApplication.run(CalendarRemainderApplication.class, args);
		  dbInit db = new dbInit();
		  db.buildTables();
	}

}
