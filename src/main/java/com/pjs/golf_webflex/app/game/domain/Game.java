package com.pjs.golf_webflex.app.game.domain;


import lombok.*;
import org.springframework.data.annotation.Id;
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
    private Long competitionId;
    private Long hostId;
    private Long fieldsId;
    private LocalDateTime playDate;
    private LocalDateTime finishDate;
    private GameProgress progress;
    private List<Long> players;

}
