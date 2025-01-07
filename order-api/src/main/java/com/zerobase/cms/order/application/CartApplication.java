package com.zerobase.cms.order.application;

import com.zerobase.cms.order.domain.product.AddProductCartForm;
import com.zerobase.cms.order.domain.redis.Cart;
import com.zerobase.cms.order.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartApplication {
    private final CartService cartService;

    public Cart addCart(Long customerId, AddProductCartForm form) {
        return cartService.addCart(customerId, form);
    }

    public Cart getCart(Long customerId) {
        return cartService.getCart(customerId);
    }

    public Cart updateCart(Long customerId, Cart cart) {
        return cartService.updateCart(customerId, cart);
    }

    public Cart refreshCart(Cart cart) {
        return cartService.refreshCart(cart);
    }
}
