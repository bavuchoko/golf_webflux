package com.pjs.golf_webflex.app.auth.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequestDto {

    private Long id;
    private String username;
    private String name;
    private String password;
    private LocalDateTime birth;
    private LocalDateTime joinDate;
    private String gender;
    private List<String> roles;

}
