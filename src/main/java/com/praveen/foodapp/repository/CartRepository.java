package com.praveen.foodapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.praveen.foodapp.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{

}
