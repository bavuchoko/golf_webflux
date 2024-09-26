package com.pjs.golf_webflex.app.game.service;

import com.pjs.golf_webflex.app.game.domain.Game;
import com.pjs.golf_webflex.common.SearchDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//내가 참가하거나 호스트인 경기만
public interface GameService {

    //목록조회
    Flux getGames(SearchDto searchDto, Long userId);

    //경기생성
    Mono createGame(Game game);

    //상세보기
    Mono getGame(Long gameId, Long userId);

    //시작하기
    Mono startGame(Long gameId, Long userId);

    //진행하기
    Mono progressGame(Long gameId, Long userId);

    //종료하기
    Mono endGame(Long gameId, Long userId);

    //참가하기
    Mono enrollGame(Long gameId, Long userId);

    //퇴장하기
    Mono expelGame(Long gameId, Long userId);

    //삭제하기
    Mono deleteGame(Long gameId, Long userId);

}
