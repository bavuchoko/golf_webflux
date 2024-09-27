package com.pjs.golf_webflex.service;

import com.pjs.golf_webflex.domain.Game;
import com.pjs.golf_webflex.dto.GameDto;
import org.springframework.data.domain.Slice;
import reactor.core.publisher.Mono;

//내가 참가하거나 호스트인 경기만
public interface GameService {

    //목록조회
    Mono<Slice<GameDto>> getGames(Long userId, int lastId, int size);

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
