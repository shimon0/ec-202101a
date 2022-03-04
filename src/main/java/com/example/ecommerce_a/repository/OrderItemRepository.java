package com.example.ecommerce_a.repository;

import com.example.ecommerce_a.domain.OrderItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class OrderItemRepository {
    @Autowired
	private NamedParameterJdbcTemplate template;

    private static final RowMapper<OrderItem> ORDER_ITEMS_ROW_MAPPER=(rs,i)->{
        OrderItem orderItem=new OrderItem();
        orderItem.setId(rs.getInt("id"));
        orderItem.setItemId(rs.getInt("item_id"));
        orderItem.setQuantity(rs.getInt("quantity"));
        return orderItem;
    };

/**
 * ユーザid 確認処理
 * @param id
 * @return
 */

    public OrderItem load(Integer id) {
        String sql="SELECT id FROM orders WHERE user_id = :userId AND status = 0; ";
        SqlParameterSource param=new MapSqlParameterSource().addValue("id", id);
        OrderItem orderItem=template.queryForObject(sql, param, ORDER_ITEMS_ROW_MAPPER);
        return orderItem;
    }
    
    /**カート追加処理
     * ordersテーブルにインサート
     */
    public  OrderItem  save(OrderItem orderItem){

    }
}
