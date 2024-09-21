package com.pjs.golf_webflex.app.auth.dto;

import lombok.Value;

import java.util.List;

@Value
public class AccountResponseDto {
    private String username;
    private String name;
    private String birth;
    private String gender;
    private List<String> roles;
}
