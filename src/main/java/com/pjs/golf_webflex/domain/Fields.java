package com.pjs.golf_webflex.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("fields")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id", callSuper = false)
public class Fields {
    @Id
    private Long id;
    private String name;
    private int totalHoleCount;
    private String address;
    private String province;
    private String detail;
    private Long creatorId;
    private Long modifierId;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private double latitude; // 위도  : 남북
    private double longitude; // 경도 : 동서


}
