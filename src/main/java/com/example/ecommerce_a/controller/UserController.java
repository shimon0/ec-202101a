package com.example.ecommerce_a.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.form.UserForm;
import com.example.ecommerce_a.service.UserService;

/**
 * ユーザーを操作するコントローラー
 * 
 * @author kashimamiyu
 *
 */
@Controller
@RequestMapping("/")
public class UserController {

	@Autowired
	private UserService userService;
	
	@ModelAttribute
	public UserForm setUpUserForm() {
		return new UserForm();
	}
	
	/**
	 * ユーザー登録画面を出力します
	 * 
	 * @return ユーザー登録画面
	 */
	@RequestMapping("/toRegister")
	public String toRegister() {
		return "register_user";
	}
	
	/**
	 * @param form ユーザー情報用フォーム
	 * @param model
	 * @return ログイン画面へリダイレクト
	 */
	@RequestMapping("/register")
	public String register (UserForm form, Model model) {
		User user = new User();
		user.setName(form.getName());
		user.setEmail(form.getEmail());
		user.setZipcode(form.getZipcode());
		user.setAddress(form.getAddress());
		user.setTelephone(form.getTelephone());
		user.setPassword(form.getPassword());
		userService.insert(user);
		return "redirect:/login";
	}
	
	
}
