package com.pjs.golf_webflex.repository;

import com.pjs.golf_webflex.domain.Game;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface GameRepository extends ReactiveCrudRepository<Game, Long> {

    Mono<Game> save(Game game);

}
