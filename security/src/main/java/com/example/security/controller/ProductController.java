package com.example.security.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.entity.Product;
import com.example.security.repository.ProductRepository;

@RestController
public class ProductController {
	
	private ProductRepository productRepo;
	

public ProductController(ProductRepository productRepo) {
		
		this.productRepo = productRepo;
	}



@GetMapping("/getProducts")
public List<Product> getProducts() {
    return productRepo.findAll();
}
@SuppressWarnings("deprecation")
@GetMapping("/id")
public Product getProducts(@PathVariable Long id) {
    return productRepo.getById(id);
}

@PostMapping("/products")
public Product saveproduct(@RequestBody Product product) {
    
	return productRepo.save(product);
	
}
}

