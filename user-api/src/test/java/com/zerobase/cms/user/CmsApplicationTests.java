package com.zerobase.cms.user;

import com.zerobase.cms.user.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
    properties = {
        "spring.main.allow-bean-definition-overriding=true",
        "spring.cloud.openfeign.enabled=false"
    }
)
@ActiveProfiles("test")
@Import(TestConfig.class)
class CmsApplicationTests {

    @Test
    void contextLoads() {
    }
}