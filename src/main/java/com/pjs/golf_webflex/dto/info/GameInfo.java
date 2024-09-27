package com.pjs.golf_webflex.dto.info;

import java.time.LocalDateTime;

public record GameInfo(
    Long id,
    Long competitionId,
    LocalDateTime playDate,
    LocalDateTime finishDate
){}
