package com.example.ecommerce_a.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
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
public class OrderHisoryRepository {
	
	  //ResultSetオブジェクトに格納された複数行分のデータをList<Clab>変数にセットしてreturnする
	  private static final RowMapper<Order> ORDER_ROW_MAPPER = (rs,i) -> {
		  List<Order> orderList=new	ArrayList<>();
	    //初めにデータを格納するための変数を宣言
	    List<OrderItem> orderItemList = new ArrayList<>(); 

	    //メンバーを格納するためのList<Member>変数を宣言(値はNullを格納しておく)
	    List<OrderTopping>toppingList = new ArrayList<>();

		Order order=new	Order();
		order.setId(rs.getInt("user_id"));
		order.



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
	    return orderList;
	  };
	@Autowired
	private NamedParameterJdbcTemplate template;
	




	  /**
		   * 注文履歴で日付を参照処理
		   * ROW_MAPPERに日付を格納
		   */
		  if(rs.getInt("order_date")!= 0){
			Order order=new Order();

		}


	
	public	List<Order> findOrderHistory(int userId){
		String	sql="SELECT ord.delivery_time ord_delivery_time, ori.id ori_id,ori.quantity ori_quantity,ori.size ori_size,itm.name itm_name,itm.price_m itm_price_m,itm.price_l itm_price_l,itm.image_path itm_image_path,top.name top_name,top.price_m top_price_m,top.price_l top_price_l,top.id top_id "
		+ "FROM orders ord "
		+ "JOIN order_items ori ON ord.id=ori.order_id "
		+ "JOIN items itm ON ori.item_id = itm.id "
		+ "LEFT OUTER JOIN order_toppings ort ON ori.id = ort.order_item_id "
		+ "LEFT OUTER JOIN toppings top ON ort.topping_id = top.id "
		+ "WHERE ord.user_id=:userId AND (ord.status=1 OR ord.status2);";

		
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId",userId);
		List<Order> orderList = template.query(sql,param,ORDER_ITEM_RESULTSET);
		System.out.println(orderList);
		return orderList;
	}

}
