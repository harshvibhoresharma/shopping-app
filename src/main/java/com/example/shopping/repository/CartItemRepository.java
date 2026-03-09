package com.example.shopping.repository;

import com.example.shopping.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long>{
    void deleteALlByCart_Id(Long id);


}
