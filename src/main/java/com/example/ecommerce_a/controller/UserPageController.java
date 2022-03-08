package com.example.ecommerce_a.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/userPage")
public class UserPageController {
	
	@Autowired
	private HttpSession session;
	
	@RequestMapping("")
	public String userPage() {
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId == null) {
			return "forward:/login";
		}
		return "user_page";
	}
}
