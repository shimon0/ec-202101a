package com.example.ecommerce_a.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.Order;
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
		HashMap<Integer,Integer>totalMap = new HashMap<>();
		List<OrderItem>orderItemList = cartService.findOrderItemList(userId);
		for(OrderItem orderItem : orderItemList) {
			totalMap.put(orderItem.getId(),orderItem.getSubTotal());
		}
		Order order	= new Order();
		order.setOrderItemList(orderItemList);
		
		if(orderItemList.size()==0) {
			orderItemList=null;
			model.addAttribute("emptyMessage","カートに商品がありません");
		}
		model.addAttribute("orderItemList",orderItemList);
		model.addAttribute("totalMap",totalMap); 
		model.addAttribute("taxTotal",order.getTax()); 
		model.addAttribute("CalcTotalPrice",order.getCalcTotalPrice()); 
		
		return "cart_list.html";
	}
	
	@RequestMapping("/delete")
	public String deleteCart(String deleteId,Model model){
		System.out.println(deleteId);
		int userId = Integer.parseInt(deleteId);
		System.out.println(userId);
		cartService.deleteCart(userId);
		return "redirect:/shoppingCart";
	}
	
}
