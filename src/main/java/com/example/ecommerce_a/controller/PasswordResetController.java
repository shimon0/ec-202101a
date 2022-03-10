package com.example.ecommerce_a.controller;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.PasswordReset;
import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.service.PasswordResetService;
import com.example.ecommerce_a.service.UserService;

@Controller
@RequestMapping("/reset")
public class PasswordResetController {
	@RequestMapping("")
	public String index() {
		return "password_reset";
	}

	@Autowired
	private MailSender mailSender;

	@Autowired
	private PasswordResetService service;

	@Autowired
	private UserService userService;

	@Autowired
	private HttpSession session;

	@RequestMapping("/resetConfirm")
	public String confirmPasswordReset(String userEmail, Model model) {
		User user = userService.findByEmail(userEmail);
		if (user == null) {
			model.addAttribute("errorMessage", "アカウントが存在しません");
			return index();
		} else if (user != null) {
			session.setAttribute("userEmailPass", userEmail);
		}
		String uniqueUrl = UUID.randomUUID().toString();

		SimpleMailMessage msg = new SimpleMailMessage();
		try {
			msg.setFrom("coffeeShopMaster2022@mail.com");
			msg.setTo(userEmail);
			msg.setSubject("パスワード変更URLの送付");
			msg.setText("http://localhost:8080/coffeeShop/reset/passwordReset?key=" + uniqueUrl);

			mailSender.send(msg);
		} catch (MailException e) {
			e.printStackTrace();
		}
		return "email_submit";
	}

	@RequestMapping("/passwordReset")
	public String index2(String key) {
		session.setAttribute("uniqueUrl", key);
		return "reset_password";
	}

	@RequestMapping("/passwordResetFinished")
	public String registerRePassword(String newPassword) {
		PasswordReset reset = new PasswordReset();
		String userEmail = (String) session.getAttribute("userEmailPass");
		reset.setUserEmail(userEmail);
		reset.setUniqueUrl((String) session.getAttribute("uniqueUrl"));
		service.insert(reset);
		System.out.println(newPassword);
		userService.updatePassword(userEmail, newPassword);
		
		return "reset_finished";
	}

}
