package com.pjs.golf_webflex.app.game.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table("game")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id", callSuper = false)
public class Game {

    @Id
    private Long id;

    private int round;

    @Column("host")
    private Long hostId;

    private int course;

    private int hole;

    @Column("play_date")
    private LocalDateTime playDate;

    @Column("field")
    private Long fieldId;

    private List<Long> playerIds;

    private List<Long> sheetIds;

    private String status;

    public void enrollGame(Long accountId) {
        List<Long> updatedPlayerIds = new ArrayList<>(this.playerIds);
        updatedPlayerIds.add(accountId);
        this.playerIds = updatedPlayerIds;
    }


}
