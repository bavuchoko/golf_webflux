package com.pjs.golf_webflex.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("memo")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Memo {

    private Long fieldsId;
    private Long accountId;
    private int courseSeq;
    private int holeSeq;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
}
