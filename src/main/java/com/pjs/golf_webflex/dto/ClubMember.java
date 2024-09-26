package com.pjs.golf_webflex.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table("club_member")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClubMember {

    private Long clubId;
    private Long accountId;
}
