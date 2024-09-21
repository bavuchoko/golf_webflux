package com.pjs.golf_webflex.app.auth.repository;

import com.pjs.golf_webflex.app.auth.adapter.AccountAdapter;
import com.pjs.golf_webflex.app.auth.dto.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface AccountRepository {


    Mono<Account> findByUsername(String nusername);
}
