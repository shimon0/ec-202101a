package com.example.ecommerce_a.controller;

import java.util.List;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shoppingList")
public class ItemController {
    @Autowired
    private ItemService service;



    @RequestMapping("")
    public String index(Model model){
        List<Item> itemList=service.findAll();
        model.addAttribute("itemList", itemList);
    //    System.out.println(itemList);
        return "item_list_coffee";
    }
    @RequestMapping("/searchWord")
    public String findByLikeWord(String searchWord,Model model){
        List<Item> itemList=service.findByLikeName(searchWord);
        if(itemList.size()==0){
            String nullMessage="該当する商品がありません";
            model.addAttribute("nullMessage", nullMessage);
            return index(model);
        }
        model.addAttribute("itemList", itemList);
        return "item_list_coffee";
    }
}
