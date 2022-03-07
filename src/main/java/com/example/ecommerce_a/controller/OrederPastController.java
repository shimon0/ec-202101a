package com.example.ecommerce_a.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orderPast")
public class OrederPastController {
    @Autowired
    private HttpSession session;

    @RequestMapping("")
    public  String  findPastOrder(Model model){
        Integer userId = (Integer) session.getAttribute("userId");
            if(userId==null){
                return  "forward:/login";
            }else{



                
                return "order_past";
            }
    }
}
