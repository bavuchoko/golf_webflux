package com.pjs.golf_webflex.app.game.service.impl;

import com.pjs.golf_webflex.app.game.domain.Game;

import com.pjs.golf_webflex.app.game.repository.GameRepository;
import com.pjs.golf_webflex.app.game.service.GameService;
import com.pjs.golf_webflex.common.SearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    @Override
    public Flux getGames(SearchDto searchDto, Long userId) {
        return null;
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
