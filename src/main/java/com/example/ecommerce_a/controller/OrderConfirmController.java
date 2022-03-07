package com.example.ecommerce_a.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.form.OrderConfirmForm;
import com.example.ecommerce_a.service.CartService;
import com.example.ecommerce_a.service.OrderConfirmService;

@Controller
@RequestMapping("/confirm")
public class OrderConfirmController {
	@Autowired
	private OrderConfirmService orderConfirmService;

	@Autowired
	private CartService cartService;

	@RequestMapping("")
	public String index(Model model) {
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId == null) {
			return "forward:/login";
		}
		List<OrderItem> orderItemList = null;
		HashMap<Integer, Integer> totalMap = new HashMap<>();
		orderItemList = cartService.findOrderItemList(userId);
		System.out.println(orderItemList);
		for (OrderItem orderItem : orderItemList) {
			totalMap.put(orderItem.getId(), orderItem.getSubTotal());
		}
		Order order = new Order();
		order.setOrderItemList(orderItemList);

		model.addAttribute("orderItemList", orderItemList);
		model.addAttribute("totalMap", totalMap);
		model.addAttribute("taxTotal", order.getTax());
		model.addAttribute("CalcTotalPrice", order.getCalcTotalPrice());

		return "order_confirm.html";
	}

	@Autowired
	private HttpSession session;

	@ModelAttribute
	private OrderConfirmForm setOrderConfirmForm() {
		return new OrderConfirmForm();
	}

	@RequestMapping("/register")
	public String register(@Validated OrderConfirmForm form, BindingResult result, Model model) throws ParseException {
		if (result.hasErrors()) {
			return index(model);
		}
		Integer userId = (Integer) session.getAttribute("userId");

		Order order = new Order();
		order.setUserId(userId);

		if (Integer.parseInt(form.getPaymentMethod()) == 1) {
			order.setStatus(1);
		} else if (Integer.parseInt(form.getPaymentMethod()) == 2) {
			order.setStatus(2);
		}

		List<OrderItem> orderItemList = null;
		
		orderItemList = cartService.findOrderItemList(userId);
		System.out.println(userId);
		System.out.println(orderItemList);
		
		Order orderPrice = new Order();
		orderPrice.setOrderItemList(orderItemList);

		order.setTotalPrice(orderPrice.getCalcTotalPrice());
		Timestamp today = new Timestamp(System.currentTimeMillis());
		order.setOrderDate(today);
		order.setDestinationName(form.getDestinationName());
		order.setDestinationEmail(form.getDestinationEmail());
		order.setDestinationZipcode(form.getDestinationZipcode());
		order.setDestinationAddress(form.getDestinationAddress());
		order.setDestinationTel(form.getDestinationTel());
		// formから取ってきた配達日時の結合
		String deliveryTime = form.getDeliveryDate() + "-" + form.getDeliveryHour();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh");
		Date deliveryTimeDate = format.parse(deliveryTime);
		Timestamp delTime = new Timestamp(deliveryTimeDate.getTime());
		LocalDateTime todayLo = today.toLocalDateTime();
		LocalDateTime delTimeLo = delTime.toLocalDateTime();
		Duration duration = Duration.between(todayLo, delTimeLo);
		System.out.println(duration.toHours());
		if (duration.toHours() < 3) {
			result.rejectValue("deliveryHour", null, "今から3時間後の日時をご入力ください");
		} else if (duration.toHours() >= 3) {
			order.setDeliveryTime(delTime);
		}
		order.setPaymentMethod(Integer.parseInt(form.getPaymentMethod()));
		System.out.println(order);

		orderConfirmService.update(order);
		return "order_finished";

	}
}
