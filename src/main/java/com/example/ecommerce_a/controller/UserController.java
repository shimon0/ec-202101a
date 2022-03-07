package com.example.ecommerce_a.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("/register")
public class UserController {

	@Autowired
	private UserService userService;
	
	/**
	 * @return UserFormをインスタンス化
	 */
	@ModelAttribute
	public UserForm setUpUserForm() {
		return new UserForm();
	}
	
	/**
	 * ユーザー登録画面を出力します
	 * 
	 * @return ユーザー登録画面
	 */
	@RequestMapping("")
	public String toRegister() {
		return "register_user";
	}
	
	/**
	 * @param form ユーザー情報用フォーム
	 * @param model
	 * @return ログイン画面へリダイレクト
	 */
	@RequestMapping("/insert")
	public String insert (@Validated UserForm form
			              ,BindingResult result
			              ) {
		
		User duplicateUser = userService.findByEmail(form.getEmail());
		
		if (duplicateUser != null) {
			result.rejectValue("email", null, "このメールアドレスは既に登録されています");
		}
		
		if(!form.getPassword().equals(form.getConfirmpassword())){
			result.rejectValue("confirmpassword", "", "パスワードが一致していません");
		}
		
		if (result.hasErrors()) {
			return "register_user.html";
		}
		
		
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
