package com.zerobase.cms.order.service;

import com.zerobase.cms.order.client.RedisClient;
import com.zerobase.cms.order.domain.product.AddProductCartForm;
import com.zerobase.cms.order.domain.redis.Cart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
    private final RedisClient redisClient;

    public Cart getCart(Long customerId) {
        Cart cart = redisClient.get(customerId, Cart.class);
        return cart != null ? cart : new Cart(customerId);
    }

    public Cart addCart(Long customerId, AddProductCartForm form) {
        Cart cart = redisClient.get(customerId, Cart.class);
        if (cart == null) {
            cart = new Cart(customerId);
        }
        Cart.Product product = Cart.Product.from(form);
        cart.getProducts().add(product);
        redisClient.put(customerId, cart);
        return cart;
    }

    public Cart updateCart(Long customerId, Cart cart) {
        redisClient.put(customerId, cart);
        return cart;
    }

    public Cart refreshCart(Cart cart) {
        return cart;
    }
}
