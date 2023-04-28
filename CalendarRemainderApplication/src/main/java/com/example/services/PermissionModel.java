package com.example.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.example.model.User;


/*
Table name : User (create the table)
schema : Username, password, email, phonenumber.
*/
@Component
public class PermissionModel {

	Connection con;
	DataSource datasource;
	
	public PermissionModel() throws SQLException {
		datasource = dbConnection.dataSource;
		con = datasource.getConnection();
	}
	
	public boolean addUser(User u) throws SQLException
	{
		//add user to db 
//		String query="CREATE TABLE User (username VARCHAR(50) NOT NULL,password VARCHAR(50) NOT NULL,Email VARCHAR(50) NOT NULL,phonenumber VARCHAR(20) NOT NULL,PRIMARY KEY (username));" ;
//		Statement stmt1 = con.createStatement();
//		stmt1.executeQuery(query);
		String sql = "INSERT INTO User (username, password, email, phonenumber) VALUES (?, ?, ?, ?)";
		PreparedStatement statement = con.prepareStatement(sql);
		statement.setString(1, u.getUsername());
		statement.setString(2, u.getPassword());
		statement.setString(3, u.getEmail());
		statement.setString(4, u.getPhoneNumber());
	
		// Execute the query to insert the row into the User table
		int numRowsInserted = statement.executeUpdate();
	
		// Check the number of rows affected by the insert operation
		if (numRowsInserted > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkPermission(String username) throws SQLException {
		//read from db and check if username already exists in db return calID
		String sql = "SELECT COUNT(*) FROM User WHERE username=?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, username);
		
		ResultSet rs = stmt.executeQuery();
		
		boolean ans = false;
		
		if (rs.next())
		{
			ans = true;
		}

		return ans;
	}	
	
	public boolean checkPermission(String username,String password) throws SQLException {
		//read from db and check if user is authorized return calID
		String sql = "SELECT COUNT(*) FROM User WHERE username=? AND password=?;";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, username);
		stmt.setString(2, password);
		ResultSet rs = stmt.executeQuery();
		 
		boolean ans = false;
		 
		
		
		if (rs.next())
		{
			int f = rs.getInt(1);
			if (f != 0)
			{
				ans = true;
			}
		}
		
		return ans;
	}
	
	public void updateAllowedUserTable(int calID) {
		//add remove user into this table along with permissions.
	}

}
