package com.pjs.golf_webflex.app.club.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("club")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Club {

    @Id
    private Long id;
    private String name;
    private Long representativeId;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String tel;
    private String officeAddress;
    private String province;
}
