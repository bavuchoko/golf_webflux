package com.pjs.golf_webflex.app.game.repository;

import com.pjs.golf_webflex.app.game.domain.Game;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface GameRepository extends ReactiveCrudRepository<Game, Long> {

    Mono<Game> save(Game game);

}
