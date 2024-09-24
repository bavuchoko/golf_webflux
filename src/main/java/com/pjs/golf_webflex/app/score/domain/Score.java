package com.pjs.golf_webflex.app.score.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table("score")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Score {
    private Long gameId;
    private Long playerId;
    private int turns;
    private int hit;
    private int penalty;
}
