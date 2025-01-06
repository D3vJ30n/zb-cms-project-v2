package com.zerobase.cms.user.service;

import com.zerobase.cms.user.config.TestConfig;
import com.zerobase.cms.user.domain.SignUpForm;
import com.zerobase.cms.user.domain.model.Customer;
import com.zerobase.cms.user.domain.repository.CustomerRepository;
import com.zerobase.cms.user.service.customer.SignUpCustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest(
    classes = {SignUpCustomerService.class, TestConfig.class},
    properties = {
        "spring.main.allow-bean-definition-overriding=true",
        "spring.cloud.openfeign.enabled=false"
    }
)
@ActiveProfiles("test")
class SignUpCustomerServiceTest {

    @Autowired
    private SignUpCustomerService service;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    void signUp() {
        // given
        SignUpForm form = SignUpForm.builder()
                .email("test@test.com")
                .name("test")
                .password("test")
                .phone("01012345678")
                .build();

        Customer customer = Customer.from(form);
        given(customerRepository.save(any()))
                .willReturn(customer);

        // when
        Customer result = service.signUp(form);

        // then
        assertNotNull(result);
        assertEquals(form.getEmail(), result.getEmail());
        assertEquals(form.getName(), result.getName());
        assertEquals(form.getPhone(), result.getPhone());
    }
}
