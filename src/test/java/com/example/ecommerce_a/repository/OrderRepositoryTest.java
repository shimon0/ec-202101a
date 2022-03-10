package com.example.ecommerce_a.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.RowMapper;

import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.OrderItem;

@SpringBootTest
class OrderRepositoryTest {

	@Autowired
	private OrderRepository repository;

	private static final RowMapper<Order> ORDER_ROW_MAPPER = (rs, i) -> {
		Order order = new Order();
		order.setId(rs.getInt("id"));
		order.setUserId(rs.getInt("user_id"));
		order.setStatus(rs.getInt("status"));
		order.setTotalPrice(rs.getInt("total_price"));
		order.setOrderDate(rs.getDate("order_date"));
		order.setDestinationName(rs.getString("destination_name"));
		order.setDestinationEmail(rs.getString("destination_email"));
		order.setDestinationZipcode(rs.getString("zipcode"));
		order.setDestinationTel(rs.getString("destination_tel"));
		order.setDeliveryTime(rs.getTimestamp("delivery_time"));
		order.setPaymentMethod(rs.getInt("payment_method"));

		return order;
	};
	

	@Test
	void testFindOrderItemList() {
		List<OrderItem> orderItemList = repository.findOrderItemList(1);
		orderItemList.forEach(s -> assertEquals(1, s.getQuantity()));
	}

	@Test
	void testDeleteCart() {

		fail("Not yet implemented");
	}

	@Test
	void testDeleteOrder() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateOrder() {
		fail("Not yet implemented");
	}

	@Test
	void testDestinationUpdate() {
		fail("Not yet implemented");
	}

	@Test
	void testFindOrderHistory() {
		List<OrderItem> orderItemList = repository.findOrderItemList(1);
		orderItemList.forEach(s -> assertEquals(1, s.getQuantity()));
	}

}
