

package com.example.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import com.example.model.User;
import com.example.services.PermissionModel;

@Component
@RestController
public class PermissionController {

	@Autowired
	PermissionModel  pm;
	public boolean addUser(User u) throws SQLException
	{
		boolean reply = pm.addUser(u);
		return reply;
	}
	
	public boolean checkPermission(String username) throws SQLException {
		boolean reply = pm.checkPermission(username);
		
		return reply;
	}
	
	
	
	public boolean checkPermission(String username,String password) throws SQLException {
		//check is user is authorized to access the calendar, asks PermissionModel.
		boolean reply = pm.checkPermission(username, password);
		
		return reply;
	}
	
	
	
}
