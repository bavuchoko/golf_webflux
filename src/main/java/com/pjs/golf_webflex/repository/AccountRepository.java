package com.pjs.golf_webflex.repository;

import com.pjs.golf_webflex.domain.Account;
import com.pjs.golf_webflex.dto.info.AccountInfo;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface AccountRepository{


    Mono<Account> findByUsername(String nusername);

    Mono<Map<Long, AccountInfo>> findUsers(List<Long> userIds);

}
