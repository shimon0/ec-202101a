package com.example.ecommerce_a.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.domain.OrderTopping;
import com.example.ecommerce_a.domain.Topping;

@Repository
public class OrderRepository {
	
	  //ResultSetオブジェクトに格納された複数行分のデータをList<Clab>変数にセットしてreturnする
	  private static final ResultSetExtractor<List<OrderItem>> ORDER_ITEM_RESULTSET = (rs) -> {
	    //初めにデータを格納するための変数を宣言
	    List<OrderItem> orderItemList = new ArrayList<>(); 

	    //メンバーを格納するためのList<Member>変数を宣言(値はNullを格納しておく)
	    List<OrderTopping>toppingList = null;

	    //clabsテーブルは結合した際に複数行にわたり同じデータが出力される可能性があるため、前のClabテーブルのIDを保持するための変数を宣言
	    int beforeIdNum = 0;


	    //ResultSetオブジェクトに格納された複数のデータをList<Clab>変数に格納していく
	    while(rs.next()) {
	      //現在検索しているClabテーブルのIDを格納するための変数を宣言
	      int nowIdNum = rs.getInt("ori_id");

	      //現在検索しているClabテーブルのIDと前のClabテーブルのIDが違う場合は新たにClabオブジェクトを作成する
	      if (nowIdNum != beforeIdNum) {
	        OrderItem orderItem = new OrderItem();
	        orderItem.setId(nowIdNum);
	        orderItem.setQuantity(rs.getInt("ori_quantity"));
	        char[] c = rs.getString("ori_size").toCharArray();
	        orderItem.setSize(c[0]);
	        
	        Item item = new Item();
	        item.setName(rs.getString("itm_name"));
	        item.setPriceM(rs.getInt("itm_price_m"));
	        item.setPriceL(rs.getInt("itm_price_l"));
	        item.setImagePath(rs.getString("itm_image_path"));
	        orderItem.setItem(item);
	        //メンバーがいた際にClabオブジェクトのmemberListに格納するため空のArrayListをセットしておく
	        toppingList = new ArrayList<OrderTopping>();
	        orderItem.setOrderToppingList(toppingList);
	        orderItemList.add(orderItem);
	      }
	      
	      

	      //ClabにMemberがいない場合はMemberオブジェクトを作成しないようにする
	      if (rs.getInt("top_id") != 0) {
	        OrderTopping orderTopping = new OrderTopping();
	        Topping topping = new Topping();
	        topping.setName(rs.getString("top_name"));
	        topping.setPriceM(rs.getInt("top_price_m"));
	        topping.setPriceL(rs.getInt("top_price_l"));
	        orderTopping.setTopping(topping);
	        //orderTopping.set(rs.getString("m_name"));
	        //memberをclabオブジェクト内にセットされているmemberListに直接追加する
	        toppingList.add(orderTopping);
	      }


	      //現在検索しているClabテーブルのIDを前のClabテーブルのIDを入れるbeforeIdNumに代入する
	      beforeIdNum = nowIdNum;
	    }
	    return orderItemList;
	  };
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	public List<OrderItem> findOrderItemList(int userId){
		String sql = "SELECT ori.id ori_id,ori.quantity ori_quantity,ori.size ori_size,itm.name itm_name,itm.price_m itm_price_m,itm.price_l itm_price_l,itm.image_path itm_image_path,top.name top_name,top.price_m top_price_m,top.price_l top_price_l,top.id top_id "
				   + "FROM orders ord "
				   + "JOIN order_items ori ON ord.id=ori.order_id "
				   + "JOIN items itm ON ori.item_id = itm.id "
				   + "LEFT OUTER JOIN order_toppings ort ON ori.id = ort.order_item_id "
				   + "LEFT OUTER JOIN toppings top ON ort.topping_id = top.id "
				   + "WHERE ord.user_id=:userId AND ord.status=0;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("userId",userId);
		List<OrderItem> orderItemList = template.query(sql,param,ORDER_ITEM_RESULTSET);
		System.out.println(orderItemList);
		return orderItemList;
	}
	
	public void deleteCart(int itemId) {
		String deleteSql = "DELETE from order_items where id = :id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id",itemId);
		template.update(deleteSql, param);
	}
	
	public void deleteOrder(int userId) {
		String deleteSql = "DELETE from orders where user_id = :userId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId",userId);
		template.update(deleteSql, param);
	}
	
	public void updateOrder(int userId,int preId) {
		String updateSql = "UPDATE orders SET user_id=:userId WHERE user_id=:preId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId",userId).addValue("preId",preId);
		template.update(updateSql, param);
	}
	
	public void destinationUpdate(Order order) {
		String updateSql = "UPDATE orders SET status=:status, total_price=:totalPrice, order_date=:orderDate, destination_name=:destinationName, destination_email=:destinationEmail, destination_zipcode=:destinationZipcode, destination_address=:destinationAddress, destination_tel=:destinationTel, delivery_time=:deliveryTime, payment_method=:paymentMethod where user_id=:userId and status=0;";
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		template.update(updateSql, param);
	}
	

	public List<OrderItem> findOrderHistory(int userId){
		String sql = "SELECT ori.id ori_id,ori.quantity ori_quantity,ori.size ori_size,itm.name itm_name,itm.price_m itm_price_m,itm.price_l itm_price_l,itm.image_path itm_image_path,top.name top_name,top.price_m top_price_m,top.price_l top_price_l,top.id top_id "
				   + "FROM orders ord "
				   + "JOIN order_items ori ON ord.id=ori.order_id "
				   + "JOIN items itm ON ori.item_id = itm.id "
				   + "LEFT OUTER JOIN order_toppings ort ON ori.id = ort.order_item_id "
				   + "LEFT OUTER JOIN toppings top ON ort.topping_id = top.id "
				   + "WHERE ord.user_id=:userId AND (ord.status=1 OR ord.status=2);";

		SqlParameterSource param = new MapSqlParameterSource().addValue("userId",userId);
		List<OrderItem> orderItemList = template.query(sql,param,ORDER_ITEM_RESULTSET);
		System.out.println(orderItemList);
		return orderItemList;
	}


}
