package com.zerobase.cms.order.application;

import com.zerobase.cms.order.domain.model.Product;
import com.zerobase.cms.order.domain.model.ProductItem;
import com.zerobase.cms.order.domain.product.AddProductCartForm;
import com.zerobase.cms.order.domain.redis.Cart;
import com.zerobase.cms.order.domain.repository.ProductRepository;
import com.zerobase.cms.order.config.TestConfig;
import com.zerobase.cms.order.service.ProductSearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestConfig.class)
@TestPropertySource(properties = {
    "spring.data.redis.port=6379",
    "spring.data.redis.host=localhost"
})
class CartApplicationTest {
    @Autowired
    private CartApplication cartApplication;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ProductSearchService productSearchService;

    @Test
    void addCart() {
        // given
        Long customerId = 1L;
        Long productId = 1L;

        AddProductCartForm.ProductItem cartItem = AddProductCartForm.ProductItem.builder()
                .id(1L)
                .name("상품 아이템")
                .count(1)
                .price(1000)
                .build();

        List<AddProductCartForm.ProductItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem);

        AddProductCartForm form = AddProductCartForm.builder()
                .id(productId)
                .sellerId(1L)
                .name("테스트 상품")
                .description("테스트 설명")
                .items(cartItems)
                .build();

        ProductItem productItem = ProductItem.builder()
                .id(1L)
                .sellerId(1L)
                .name("상품 아이템")
                .price(1000)
                .count(1)
                .build();

        Product product = Product.builder()
                .id(productId)
                .sellerId(1L)
                .name("테스트 상품")
                .description("테스트 설명")
                .productItems(List.of(productItem))
                .build();

        // Mock 설정
        given(productSearchService.getByProductId(anyLong()))
                .willReturn(product);
        given(productRepository.findById(anyLong()))
                .willReturn(Optional.of(product));

        // when
        Cart cart = cartApplication.addCart(customerId, form);

        // then
        assertNotNull(cart);
        assertEquals(1, cart.getProducts().size());
        
        Cart.Product cartProduct = cart.getProducts().get(0);
        assertEquals(productId, cartProduct.getId());
        assertEquals(1, cartProduct.getItems().size());
        
        Cart.ProductItem resultItem = cartProduct.getItems().get(0);
        assertEquals(cartItem.getName(), resultItem.getName());
        assertEquals(cartItem.getCount(), resultItem.getCount());
        assertEquals(cartItem.getPrice(), resultItem.getPrice());
    }
}