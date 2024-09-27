package com.pjs.golf_webflex.service.impl;

import com.pjs.golf_webflex.domain.Game;

import com.pjs.golf_webflex.dto.GameDto;
import com.pjs.golf_webflex.dto.info.AccountInfo;
import com.pjs.golf_webflex.dto.info.CompetitionInfo;
import com.pjs.golf_webflex.dto.info.FieldsInfo;
import com.pjs.golf_webflex.repository.AccountRepository;
import com.pjs.golf_webflex.repository.FieldsRepository;
import com.pjs.golf_webflex.repository.GameRepository;
import com.pjs.golf_webflex.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final AccountRepository accountRepository;
    private final FieldsRepository fieldsRepository;

    @Override
    public Mono<Slice<GameDto>> getGames(Long accountId, int page, int size) {
        int offset = page * size;
        return gameRepository.findMyGames(accountId, size, offset)
                .collectList() // Flux<> -> Mono<List<>>
                .flatMap(games -> {
                    // List<>로 변환된 후 stream() 사용 가능
                    List<Long> gameIds = games.stream().map(Game::getId).filter(Objects::nonNull).collect(Collectors.toList());

                    if (games.isEmpty()) {
                        return Mono.just(new SliceImpl<>(List.of(), PageRequest.of(page, size), false));
                    }

                    List<Long> accountIds = games.stream().map(Game::getHostId).filter(Objects::nonNull).collect(Collectors.toList());

                    List<Long> fieldsIds = games.stream().map(Game::getFieldsId).filter(Objects::nonNull).collect(Collectors.toList());

                    return gameRepository.findAllPlayers(gameIds)
                            .flatMap(playerIds -> {
                                accountIds.addAll(playerIds);
                                Mono<Map<Long, AccountInfo>> accountMono = accountRepository.findUsers(accountIds);
                                Mono<Map<Long, FieldsInfo>> fieldsMono = fieldsRepository.findFieldsByGameIds(fieldsIds);

                                return Mono.zip(accountMono, fieldsMono).flatMap(tuple -> {
                                    Map<Long, AccountInfo> userInfoMap = tuple.getT1();
                                    Map<Long, FieldsInfo> fieldsInfoMap = tuple.getT2();

                                    // GameDto 리스트 생성
                                    List<GameDto> gameDtos = games.stream()
                                            .map(game -> GameDto.builder()
                                                    .id(game.getId())
                                                    .competition(new CompetitionInfo(game.getCompetitionId(), null))
                                                    .host(userInfoMap.isEmpty()? null : userInfoMap.get(game.getHostId()))
                                                    .fields(fieldsInfoMap.isEmpty() ? null : fieldsInfoMap.get(game.getFieldsId()))
                                                    .playDate(game.getPlayDate())
                                                    .finishDate(game.getFinishDate())
                                                    .progress(game.getProgress())
                                                    .players(game.getPlayers().stream()
                                                            .map(playerId -> userInfoMap.getOrDefault(playerId, null))
                                                            .filter(Objects::nonNull)
                                                            .collect(Collectors.toList()))
                                                    .build())
                                            .collect(Collectors.toList());

                                    // Slice<GameDto> 반환
                                    return Mono.just(new SliceImpl<>(gameDtos, PageRequest.of(page, size), gameDtos.size() == size));
                                }).doOnError(e -> {
                                    System.err.println("Error occurred: " + e.getMessage());
                                });
                            });
                });
    }

    @Override
    public Mono createGame(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public Mono getGame(Long gameId, Long userId) {
        return null;
    }

    @Override
    public Mono startGame(Long gameId, Long userId) {
        return null;
    }

    @Override
    public Mono progressGame(Long gameId, Long userId) {
        return null;
    }

    @Override
    public Mono endGame(Long gameId, Long userId) {
        return null;
    }

    @Override
    public Mono enrollGame(Long gameId, Long userId) {
        return null;
    }

    @Override
    public Mono expelGame(Long gameId, Long userId) {
        return null;
    }

    @Override
    public Mono deleteGame(Long gameId, Long userId) {
        return null;
    }
}
