package com.zerobase.domain.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserVo {
    private String email;
    private Long id;
    private UserType userType;
} 