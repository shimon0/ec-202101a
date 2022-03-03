package com.example.ecommerce_a.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.form.OrderConfirmForm;

@Controller
@RequestMapping("/confirm")
public class OrderConfirmContoroller {
	@RequestMapping("")
	public String index() {
		return "order_confirm";
	}

	@RequestMapping("/register")
	public String register(@Validated OrderConfirmForm form, BindingResult result) {
		Order order = new Order();
		order.setStatus(null);
		order.setTotalPrice(null);
		Date today = new Date();
		order.setOrderDate(today);
		order.setDestinationName(form.getDestinationName());
		order.setDestinationEmail(form.getDestinationEmail());
		order.setDestinationZipcode(form.getDestinationZipcode());
		order.setDestinationAddress(form.getDestinationAddress());
		order.setDestinationTel(form.getDestinationTel());
		order.setDelivertyTime(form.getDelivertyTime());
		order.setPaymentMethod(Integer.parseInt(form.getPaymentMethod()));

		return "order_finished";

	}
}
