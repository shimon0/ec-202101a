package com.example.ecommerce_a.repository;

import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.OrderItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.thymeleaf.standard.expression.OrExpression;

@Repository
public class OrderItemRepository {
    @Autowired
	private NamedParameterJdbcTemplate template;

    private static final RowMapper<OrderItem> ORDER_ITEMS_ROW_MAPPER=(rs,i)->{
        OrderItem orderItem=new OrderItem();
        orderItem.setId(rs.getInt("id"));
        orderItem.setItemId(rs.getInt("item_id"));
        orderItem.setQuantity(rs.getInt("quantity"));
        char[] c = rs.getString("size").toCharArray();
        orderItem.setSize(c[0]);
        return orderItem;
    };

    private static final RowMapper<Order> ORDER_ROW_MAPPER=(rs,i)->{
        Order order=new Order();
        order.setId(rs.getInt("id"));

        return order;
    };

    /**
    * ユーザid 確認処理
    * @param id
    * @return
    */

    public OrderItem load(Integer id) {
        String sql="SELECT id FROM orders WHERE user_id = :userId AND status = 0; ";
        SqlParameterSource param=new MapSqlParameterSource().addValue("id", id);
        Order order=template.queryForObject(sql, param, ORDER_ROW_MAPPER);
        OrderItem orderItem=new OrderItem();
        orderItem.setOrderId(order.getId());
        return orderItem;
    }
    
    /**
     * カート追加処理
     * ordersテーブルにインサート
     */
    
     public int insert(OrderItem orderItem){
        String sql="INSERT INTO order_items(item_id,order_id,quantity,size)VALUES(':itemId',':orederId',':quantity',':size') RETURNING id;";
        SqlParameterSource param=new MapSqlParameterSource().addValue("orederId'", orderItem.getOrderId())
        .addValue("itemId", orderItem.getItemId()).addValue("quantity", orderItem.getQuantity()).addValue("size",orderItem.getSize());

        OrderItem orderItemId=template.queryForObject(sql, param,ORDER_ITEMS_ROW_MAPPER);
        return orderItemId.getId();
        
     } 
}
