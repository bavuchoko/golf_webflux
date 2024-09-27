package com.pjs.golf_webflex.dto;

import com.pjs.golf_webflex.domain.Account;
import com.pjs.golf_webflex.domain.Game;
import com.pjs.golf_webflex.domain.GameProgress;
import com.pjs.golf_webflex.dto.info.AccountInfo;
import com.pjs.golf_webflex.dto.info.CompetitionInfo;
import com.pjs.golf_webflex.dto.info.FieldsInfo;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameDto {

    private Long id;
    private CompetitionInfo competition;

    private AccountInfo host;

    private FieldsInfo fields;

    private LocalDateTime playDate;
    private LocalDateTime finishDate;
    private GameProgress progress;

    private List<AccountInfo> players;

    public Game toEntity(Account account) {
        return Game.builder()
                .competitionId(this.competition==null ? null : this.competition.id())
                .hostId(this.host ==null ? account.getId() : this.host.id())
                .fieldsId(this.fields==null ? null : this.fields.id())
                .playDate(this.playDate == null ? LocalDateTime.now() : this.playDate)
                .finishDate(this.finishDate ==null ? null : this.finishDate)
                .build();
    }
}
