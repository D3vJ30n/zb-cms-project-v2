package com.zerobase.cms.order.application;

import com.zerobase.cms.order.config.TestJwtConfig;
import com.zerobase.cms.order.domain.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestJwtConfig.class)
class CartApplicationTest {

    @Autowired
    private CartApplication cartApplication;

    @MockBean
    private ProductRepository productRepository;

    @Test
    void ADD_TEST_MODIFY() {
        // 테스트 코드
    }
}