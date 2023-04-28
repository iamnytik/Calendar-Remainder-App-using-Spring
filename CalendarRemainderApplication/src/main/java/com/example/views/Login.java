package com.example.views;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

//import com.example.controller.permissionController;

@Component
public class Login{
	
	private static volatile Login L;
	public static Login getLoginInstance()
	{
		if (L == null)
		{
			synchronized (Login.class) 
			{
				if (L == null)
				{
					L = new Login();
				}
			}
		}
		
		return L;
		
	}

	public ModelAndView DisplayLoginForm()
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("Login.jsp");
		return mv;
	}
	
}
