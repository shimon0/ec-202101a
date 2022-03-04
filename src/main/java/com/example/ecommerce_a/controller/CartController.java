package com.example.ecommerce_a.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.service.CartService;

@Controller
@RequestMapping("/shoppingCart")
public class CartController {
	@Autowired
	private CartService cartService;
	
	@RequestMapping("")
	public String findOrderItemList(Model model){
		int userId=1;
		System.out.println(1);
		List<OrderItem>orderItemList = cartService.findOrderItemList(userId);
		System.out.println(2);
		orderItemList.forEach(s->System.out.println(s));
		model.addAttribute(orderItemList);
		return "cart_list.html";
	}
}
