package com.zerobase.cms.order.service;

import com.zerobase.cms.order.domain.model.Product;
import com.zerobase.cms.order.domain.model.ProductItem;
import com.zerobase.cms.order.domain.product.AddProductForm;
import com.zerobase.cms.order.domain.repository.ProductRepository;
import com.zerobase.cms.order.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestConfig.class)
@TestPropertySource(properties = {
    "spring.data.redis.port=6379",
    "spring.data.redis.host=localhost"
})
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    void addProduct() {
        // given
        Long sellerId = 1L;
        AddProductForm form = createAddProductForm();
        Product product = createProduct(sellerId);
        given(productRepository.save(any())).willReturn(product);

        // when
        Product result = productService.addProduct(sellerId, form);

        // then
        assertNotNull(result);
        assertEquals(sellerId, result.getSellerId());
        assertEquals(1, result.getProductItems().size());
    }

    private AddProductForm createAddProductForm() {
        AddProductForm.ProductItem item = AddProductForm.ProductItem.builder()
                .name("상품 아이템")
                .price(1000)
                .count(1)
                .build();

        return AddProductForm.builder()
                .name("테스트 상품")
                .description("테스트 설명")
                .items(List.of(item))
                .build();
    }

    private Product createProduct(Long sellerId) {
        ProductItem productItem = ProductItem.builder()
                .id(1L)
                .sellerId(sellerId)
                .name("상품 아이템")
                .price(1000)
                .count(1)
                .build();

        Product product = Product.builder()
                .id(1L)
                .sellerId(sellerId)
                .name("테스트 상품")
                .description("테스트 설명")
                .build();

        product.getProductItems().add(productItem);
        productItem.setProduct(product);
        
        return product;
    }
}