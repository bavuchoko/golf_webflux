package com.pjs.golf_webflex.repository;

import com.pjs.golf_webflex.domain.Game;
import com.pjs.golf_webflex.dto.GameDto;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface GameRepository {

    Mono<Game> save(Game game);



    Flux<Game> findMyGames(Long accountId, int limit, int offset);



    Mono<Boolean> hasNextGames(Long accountId, int offset);



    Mono<List<Long>> findAllPlayers(List<Long> gameIds);

}
