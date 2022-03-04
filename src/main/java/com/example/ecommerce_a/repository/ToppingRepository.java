package com.example.ecommerce_a.repository;

import java.util.List;

import com.example.ecommerce_a.domain.Topping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ToppingRepository {
    @Autowired
	private NamedParameterJdbcTemplate template;

    private static final RowMapper<Topping> TOPPING_ROW_MAPPER=(rs,i)->{
        Topping topping=new Topping();
        topping.setId(rs.getInt("id"));
        topping.setName(rs.getString("name"));
        topping.setPriceL(rs.getInt("price_l"));
        topping.setPriceM(rs.getInt("price_m"));
        return topping;
    };

    public List<Topping> findAll(){
        String sql="SELECT * FROM toppings";
        List<Topping> toppingList=template.query(sql,TOPPING_ROW_MAPPER);
        return toppingList;

    }

    //使用しない
    /*public Topping load(Integer id) {
		String sql="SELECT id,name,  price_m,price_l FROM toppings WHERE id=:id";
		SqlParameterSource param=new MapSqlParameterSource().addValue("id", id);
        Topping topping=template.queryForObject(sql, param, TOPPING_ROW_MAPPER);
        return topping;
    }
    */
}
