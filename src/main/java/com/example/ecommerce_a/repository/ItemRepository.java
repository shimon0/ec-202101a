package com.example.ecommerce_a.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_a.domain.Item;

/**
 * @author smone
 *
 */
@Repository
public class ItemRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<Item> ITEM_ROW_MAPPER=new BeanPropertyRowMapper<>(Item.class);
	
	/**
	 * 全件検索を行う
	 * @return 商品一覧
	 */
	public List<Item> findAll(){
		String sql="SELECT name,price_m,price_l,image_path FROM items　ORDER BY  price_m  ASC ;";
		List<Item> itemList=template.query(sql, ITEM_ROW_MAPPER);
		
		return itemList;
	}
	
	/**
	 * 商品の曖昧検索を行う
	 * @param searchWord
	 * @return　商品一覧
	 */
	public List<Item> findByLikeName(String searchWord) {
		String sql="SELECT name,price_m,price_l,image_path FROM items　WHERE　name like :name;";
		SqlParameterSource param=new MapSqlParameterSource().addValue("name", "%"+searchWord+"%");
		List<Item> itemList=template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}
	
	public Item load(Integer id) {
		String sql="SELECT name,  price_m,price_l,image_path,description FROM items WHERE　id=:id";
		SqlParameterSource param=new MapSqlParameterSource().addValue("id", id);
		Item item=template.queryForObject(sql, param, ITEM_ROW_MAPPER);
		return item;
	}
}
