package com.pjs.golf_webflex.app.auth.dto;

import lombok.*;

@Value
public class LoginRequestDto {
    private String username;
    private String password;
}
