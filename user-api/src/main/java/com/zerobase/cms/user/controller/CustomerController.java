package com.zerobase.cms.user.controller;

import com.zerobase.cms.user.domain.customer.ChangeBalanceForm;
import com.zerobase.cms.user.domain.customer.CustomerDto;
import com.zerobase.cms.user.domain.model.Customer;
import com.zerobase.cms.user.exception.CustomException;
import com.zerobase.cms.user.exception.ErrorCode;
import com.zerobase.cms.user.service.customer.CustomerBalanceService;
import com.zerobase.cms.user.service.customer.CustomerService;
import com.zerobase.domain.config.UserVo;
import com.zerobase.domain.config.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final CustomerService customerService;
    private final CustomerBalanceService customerBalanceService;

    @GetMapping("/getInfo")
    public ResponseEntity<CustomerDto> getInfo(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
        UserVo vo = jwtAuthenticationProvider.getUserVo(token);
        Customer customer = customerService.findByIdAndEmail(vo.getId(), vo.getEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return ResponseEntity.ok(CustomerDto.from(customer));
    }

    @PostMapping("/balance")
    public ResponseEntity<Integer> changeBalance(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                 @RequestBody ChangeBalanceForm form) {
        UserVo vo = jwtAuthenticationProvider.getUserVo(token);
        return ResponseEntity.ok(customerBalanceService.changeBalance(vo.getId(), form).getCurrentMoney());
    }
}
