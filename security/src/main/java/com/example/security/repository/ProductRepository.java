package com.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {

}
