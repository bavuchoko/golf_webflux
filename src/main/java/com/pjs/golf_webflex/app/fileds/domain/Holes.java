package com.pjs.golf_webflex.app.fileds.domain;


import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("holes")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Holes {

    private int courseSeq;
    private int holeSeq;
    private Long fieldsId;
    private int distance;
    private int par;
    private String detail;
    private Long creatorId;
    private Long modifierId;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
}
