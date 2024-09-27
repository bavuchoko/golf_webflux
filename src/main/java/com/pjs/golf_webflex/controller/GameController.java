package com.pjs.golf_webflex.controller;


import com.pjs.golf_webflex.domain.Account;
import com.pjs.golf_webflex.domain.Game;
import com.pjs.golf_webflex.dto.GameDto;
import com.pjs.golf_webflex.service.GameService;
import com.pjs.golf_webflex.config.annotation.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping(value = "/api/vi/game",  produces = "application/json;charset=UTF-8")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;


    @PostMapping
    @PreAuthorize("hasAnyRole('USER')")
    public Mono<Long> saveGame(
            @CurrentUser Account account,
            @RequestBody GameDto gameRequestDto) {
        return gameService.createGame(gameRequestDto.toEntity(account));
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('USER')")
    public Mono<Slice<GameDto>> getMyGames(
            @CurrentUser Account account,
            @RequestParam("page") int page,
            @RequestParam("size") int size
            ) {

        return gameService.getGames(account.getId(), page, size)
                .doOnError(error -> log.error("Error fetching games", error))
                .onErrorResume(ex -> Mono.empty());
    }

}
