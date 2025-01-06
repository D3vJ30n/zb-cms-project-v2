package com.zerobase.cms.user.controller;

import com.zerobase.cms.user.service.seller.SellerService;
import com.zerobase.domain.config.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerController {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final SellerService sellerService;


}
