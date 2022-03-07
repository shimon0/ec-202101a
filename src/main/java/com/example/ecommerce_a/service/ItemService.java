package com.example.ecommerce_a.service;

import java.util.List;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.repository.ItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ItemService {
    @Autowired
    private ItemRepository repository;

    public List<Item> findAll(){
        return repository.findAll();
    }

    public List<Item> findByLikeName(String searchWord){
        return repository.findByLikeName(searchWord);
    }

    public Item load(Integer id){
        return repository.load(id);
    }
    
}
