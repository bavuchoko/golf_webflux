package com.pjs.golf_webflex.dto;

import com.pjs.golf_webflex.domain.Account;
import com.pjs.golf_webflex.domain.Game;
import com.pjs.golf_webflex.domain.GameProgress;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameRequestDto {

    private Long competitionId;
    private Long hostId;
    private Long fieldsId;
    private LocalDateTime playDate;
    private LocalDateTime finishDate;
    private GameProgress progress;
    private List<Long> players;

    public Game toEntity(Account account) {
        return Game.builder()
                .competitionId(this.competitionId)
                .hostId(account.getId())
                .fieldsId(this.fieldsId)
                .playDate(this.playDate)
                .finishDate(this.finishDate)
                .progress(this.progress)
                .build();
    }
}
