package com.zerobase.cms.user.client.mailgun;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class MailgunAuthInterceptor implements RequestInterceptor {

    @Value("${mailgun.key}")
    private String mailgunKey;

    @Override
    public void apply(RequestTemplate template) {
        String credentials = "api:" + mailgunKey;
        String encodedCredentials = Base64.getEncoder()
            .encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        template.header("Authorization", "Basic " + encodedCredentials);
    }
}
