package com.pjs.golf_webflex.app.game.dto;

import com.pjs.golf_webflex.app.auth.dto.AccountResponseDto;
import com.pjs.golf_webflex.app.game.domain.GameProgress;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameResponseDto {

    private Long id;
    private Long competitionId;
    private String hostName;
    private String fieldsName;
    private LocalDateTime playDate;
    private LocalDateTime finishDate;
    private GameProgress progress;

    private List<AccountResponseDto> players;
}
