package com.pjs.golf_webflex.app.game.domain;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Table("game")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Game {

    @Id
    private Long id;
    @Column("competition_id")
    private Long competitionId;
    @Column("host_id")
    private Long hostId;
    @Column("fields_id")
    private Long fieldsId;
    @Column("play_date")
    private LocalDateTime playDate;
    @Column("finish_date")
    private LocalDateTime finishDate;
    @Column("progress")
    private GameProgress progress;
    @Transient
    private List<Long> players;

}
