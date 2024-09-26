package com.pjs.golf_webflex.app.game;


import com.pjs.golf_webflex.app.auth.domain.Account;
import com.pjs.golf_webflex.app.game.domain.Game;
import com.pjs.golf_webflex.app.game.service.GameService;
import com.pjs.golf_webflex.config.annotation.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/vi/game",  produces = "application/json;charset=UTF-8")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;


    @PostMapping
    @PreAuthorize("hasAnyRole('USER')")
    public Mono<Long> saveGame(
            @CurrentUser Account account,
            @RequestBody Game game) {

        return gameService.createGame(game);
    }

}
