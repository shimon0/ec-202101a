package com.example.ecommerce_a.controller;

import java.util.List;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.domain.Topping;
import com.example.ecommerce_a.repository.OrderItemRepository;
import com.example.ecommerce_a.service.ItemDetailService;
import com.example.ecommerce_a.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/itemDetail")
public class ItemDetailController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemDetailService itemDetailService;

    @Autowired
    private OrderItemRepository repository;

    @RequestMapping("")
    public  String index(String id,Model model){
        Item item=itemService.load(Integer.parseInt(id));
        if(item.getName()==null){
            return "redirect:/shoppingList";

        }
        List<Topping> toppingList=itemDetailService.findAll();
        model.addAttribute("item",item);
        model.addAttribute("toppingList",toppingList);
        return "item_detail";
    }

    @RequestMapping("/insert")
    public String insert(){
        repository.load(1);
        repository.insert(orderItem);
        return "/shoppingCart";
    }
    

}
