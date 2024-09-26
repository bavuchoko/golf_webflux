package com.pjs.golf_webflex.dto;

import com.pjs.golf_webflex.domain.GameProgress;
import lombok.*;

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
