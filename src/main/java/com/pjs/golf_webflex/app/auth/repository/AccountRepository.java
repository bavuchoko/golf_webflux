package com.pjs.golf_webflex.app.auth.repository;

import com.pjs.golf_webflex.app.auth.domain.Account;
import reactor.core.publisher.Mono;

public interface AccountRepository {


    Mono<Account> findByUsername(String nusername);
}
