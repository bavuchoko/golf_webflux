package com.pjs.golf_webflex.app.auth.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Long id;
    private String username;
    private String name;
    private String password;
    private LocalDateTime birth;
    private String gender;
    private List<String> roles;
}
