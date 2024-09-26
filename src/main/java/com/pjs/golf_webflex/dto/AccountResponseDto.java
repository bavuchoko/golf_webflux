package com.pjs.golf_webflex.dto;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class AccountResponseDto {

    private Long id;
    private String username;
    private String name;
    private String password;
    private LocalDateTime birth;
    private LocalDateTime joinDate;
    private String gender;
    private List<String> roles;
}
