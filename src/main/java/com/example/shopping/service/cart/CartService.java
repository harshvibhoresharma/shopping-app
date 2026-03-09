package com.example.shopping.service.cart;

import com.example.shopping.exceptions.ResourceNotFoundException;
import com.example.shopping.model.Cart;
import com.example.shopping.model.CartItem;
import com.example.shopping.repository.CartItemRepository;
import com.example.shopping.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@RequiredArgsConstructor
@Service
public class CartService implements iCartService{
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    @Override
    public Cart getCart(Long id) {
        return  cartRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("cart not found"));
    }
    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteALlByCart_Id(id);
        cart.getCartItems().clear();
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getCartItems()
                .stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }
}
