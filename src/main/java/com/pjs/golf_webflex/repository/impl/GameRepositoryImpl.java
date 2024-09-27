package com.pjs.golf_webflex.repository.impl;

import com.pjs.golf_webflex.domain.Game;
import com.pjs.golf_webflex.domain.GameProgress;
import com.pjs.golf_webflex.dto.info.AccountInfo;
import com.pjs.golf_webflex.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class GameRepositoryImpl implements GameRepository {

    private final DatabaseClient databaseClient;


    @Override
    public Mono<Game> save(Game game) {
        return null;
    }

    @Override
    public Flux<Game> findMyGames(Long accountId, int limit, int offset) {
        return databaseClient.sql(
            """
            SELECT  
               g.id as id ,
               g.competition_id as competitionId ,
               g.host_id as hostId ,
               g.fields_id as fieldsId ,
               g.play_date as playDate ,
               g.finish_date as finishDate ,
               gp.game_id as gameId,
               gp.turns as turns,
               gp.half as half,
               gp.hole as hole,
               gp.progress_time as progressTime,
               gp.status as status,
               p.account_id as playerId  
            FROM game g  
            LEFT JOIN game_progress gp ON g.id = gp.game_id  
            LEFT JOIN game_player p ON g.id = p.game_id  
            WHERE EXISTS (SELECT 1 FROM game_player WHERE game_id = g.id AND account_id = :accountId)  
            OR g.host_id = :accountId 
            ORDER BY play_date DESC
            LIMIT :limit OFFSET :offset
            """)
            .bind("accountId", accountId)
            .bind("limit", limit)
            .bind("offset", offset)
            .fetch().all()
            .sort(Comparator.comparing(result -> (Long) result.get("id"))) // 게임 ID로 정렬
            .bufferUntilChanged(result -> result.get("id"))
            .map(result -> {
                var players = result.stream()
                        .map(row -> row.get("playerId") != null ? (Long) row.get("playerId") : null) // 플레이어 ID 수집
                        .filter(Objects::nonNull) // null 제거
                        .toList();

                var row = result.get(0);
                GameProgress progress = GameProgress.builder()
                    .gameId((Long) row.get("gameId"))
                    .turn((Integer) row.get("turn"))
                    .half((Integer) row.get("half"))
                    .hole((Integer) row.get("hole"))
                    .progressTime((LocalDateTime) row.get("progressTime"))
                    .progressTime((LocalDateTime) row.get("progressTime"))
                    .status((String) row.get("status"))
                .build();

                Game game = Game.builder()
                    .id((Long) row.get("id"))
                    .competitionId((Long) row.get("competitionId"))
                    .hostId((Long) row.get("hostId"))
                    .fieldsId((Long) row.get("fieldsId"))
                    .playDate((LocalDateTime) row.get("playDate"))
                    .finishDate((LocalDateTime) row.get("finishDate"))
                    .players(players)
                    .progress(progress)
                .build();

                return game;
            });
    }

    @Override
    public Mono<Boolean> hasNextGames(Long accountId, int offset) {
        return databaseClient.sql(
                        """
                        SELECT EXISTS( 
                            SELECT 1 FROM game g 
                            WHERE EXISTS (SELECT 1 FROM game_player WHERE game_id = g.id AND account_id = :accountId) 
                            OR g.host_id = :accountId 
                            LIMIT 1 OFFSET :offset
                        )
                        """)
                .bind("accountId", accountId)
                .bind("offset", offset)
                .map(row -> row.get(0, Boolean.class))
                .one();
    }

    @Override
    public Mono<List<Long>> findAllPlayers(List<Long> gameIds) {
        return databaseClient.sql(
                        """
                        SELECT account_id 
                        FROM game_player 
                        WHERE game_id IN (:gameIds)
                        """)
                .bind("gameIds", gameIds) // bindList를 사용하여 리스트 바인딩
                .fetch()
                .all() // 결과를 가져옴
                .map(row -> (Long)row.get("account_id"))
                .collectList();
    }
}
