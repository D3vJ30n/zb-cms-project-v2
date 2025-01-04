package com.zerobase.cms.user.service;

import com.zerobase.cms.user.application.SignUpApplication;
import com.zerobase.cms.user.config.TestConfig;
import com.zerobase.cms.user.domain.model.Customer;
import com.zerobase.cms.user.domain.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestConfig.class)
class SignUpCustomerServiceTest {

    @Autowired
    private SignUpApplication signUpApplication;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    void signUp() {
        // given
        Customer customer = Customer.builder()
                .email("test@test.com")
                .name("test")
                .password("test")
                .build();
                
        when(customerRepository.save(any())).thenReturn(customer);
        
        // when & then
        // ... 테스트 로직
    }
}
