package com.pjs.golf_webflex.app.game.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("game_progress")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameProgress {

    private Long gameId;
    private Integer turn;
    private Integer half;
    private Integer hole;
    private LocalDateTime progressTime;
    private String status;

    public void progress(){

    }
}
