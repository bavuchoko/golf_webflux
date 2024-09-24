package com.pjs.golf_webflex.app.auth.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Account {
    @Id
    private Long id;
    private String username;
    private String name;
    private String password;
    private LocalDateTime birth;
    private String gender;
    private List<String> roles;
}
