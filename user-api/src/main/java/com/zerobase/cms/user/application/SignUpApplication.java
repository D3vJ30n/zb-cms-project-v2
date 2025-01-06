package com.zerobase.cms.user.application;

import com.zerobase.cms.user.domain.SignUpForm;
import com.zerobase.cms.user.domain.model.Customer;
import com.zerobase.cms.user.domain.model.Seller;
import com.zerobase.cms.user.exception.CustomException;
import com.zerobase.cms.user.exception.ErrorCode;
import com.zerobase.cms.user.service.customer.SignUpCustomerService;
import com.zerobase.cms.user.service.seller.SignUpSellerService;
import com.zerobase.cms.user.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpApplication {
    private final SignUpCustomerService signUpCustomerService;
    private final SignUpSellerService signUpSellerService;
    private final EmailService emailService;

    public void customerVerify(String email, String code) {
        signUpCustomerService.verifyEmail(email, code);
    }

    public void sellerVerify(String email, String code) {
        signUpSellerService.verifyEmail(email, code);
    }

    public String customerSignUp(SignUpForm form) {
        if (signUpCustomerService.isEmailExist(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTERED_USER);
        }
        Customer c = signUpCustomerService.signUp(form);

        String code = getRandomCode();
        String verificationEmail = getVerificationEmailBody(c.getEmail(), c.getName(), "customer", code);
        
        emailService.sendEmail(
            form.getEmail(),
            "Verification Email",
            verificationEmail
        );
        
        signUpCustomerService.changeCustomerValidateEmail(c.getId(), code);
        return "회원 가입에 성공하였습니다.";
    }

    public String sellerSignUp(SignUpForm form) {
        if (signUpSellerService.isEmailExist(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTERED_USER);
        }
        Seller s = signUpSellerService.signUp(form);

        String code = getRandomCode();
        String verificationEmail = getVerificationEmailBody(s.getEmail(), s.getName(), "seller", code);
        
        emailService.sendEmail(
            form.getEmail(),
            "Verification Email",
            verificationEmail
        );
        
        signUpSellerService.changeSellerValidateEmail(s.getId(), code);
        return "회원 가입에 성공하였습니다.";
    }

    private String getRandomCode() {
        return RandomStringUtils.random(10, true, true);
    }

    private String getVerificationEmailBody(String email, String name, String type, String code) {
        StringBuilder builder = new StringBuilder();
        return builder.append("Hello ").append(name).append("! Please Click Link for verification.\n\n")
                .append("http://localhost:8081/signup/")
                .append(type)
                .append("/verify?email=")
                .append(email)
                .append("&code=")
                .append(code).toString();
    }
}
