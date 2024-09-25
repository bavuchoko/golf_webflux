package com.pjs.golf_webflex.app.game.repository;

import com.pjs.golf_webflex.app.game.domain.Game;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

interface GameCustomRepository extends R2dbcRepository<Game, Long> {
}
