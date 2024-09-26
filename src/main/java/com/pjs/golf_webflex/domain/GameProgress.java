package com.pjs.golf_webflex.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
        int base = 9;
        int remain = this.hole % base;

        if( remain != 0){
            ++hole;
        }else{
            hole =1;
        }
        ++turn;
        this.half = ((this.turn - 1)/9) + 1;
    }
}
