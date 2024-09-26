package com.pjs.golf_webflex.repository;

import com.pjs.golf_webflex.domain.Account;
import reactor.core.publisher.Mono;

public interface AccountRepository {


    Mono<Account> findByUsername(String nusername);
}
