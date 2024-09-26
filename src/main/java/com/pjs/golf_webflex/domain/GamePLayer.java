package com.pjs.golf_webflex.domain;


import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("game_player")
public class GamePLayer {

    @Column("game_id")
    private Long gameId;

    @Column("account_id")
    private Long accountId;
}
