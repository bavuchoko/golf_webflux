package com.pjs.golf_webflex.app.auth.repository.impl;

import com.pjs.golf_webflex.app.auth.domain.Account;
import com.pjs.golf_webflex.app.auth.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import org.springframework.r2dbc.core.DatabaseClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository{

    private final DatabaseClient databaseClient;

    public Mono<Account> findByUsername(String username) {
        return databaseClient.sql(
                        "SELECT a.id, a.username, a.name, a.password, a.birth, a.gender, ar.role " +
                                "FROM account a " +
                                "JOIN account_roles ar ON a.id = ar.account_id "+
                                "WHERE a.username = :username"
                )
                .bind("username", username)
                .map((row, metadata) -> {
                    // Map Account fields
                    Account account = new Account();
                    account.setId(row.get("id", Long.class));
                    account.setUsername(row.get("username", String.class));
                    account.setName(row.get("name", String.class));
                    account.setPassword(row.get("password", String.class));
                    account.setBirth(row.get("birth", LocalDateTime.class));
                    account.setGender(row.get("gender", String.class));
                    account.setRoles(new ArrayList<>()); // Initialize empty list for roles
                    account.getRoles().add(row.get("role", String.class)); // Add the first role
                    return account;
                })
                .all()
                .collectList()
                .map(accounts -> {
                    Account result = accounts.get(0); // Assuming one account
                    result.setRoles(accounts.stream()
                            .map(Account::getRoles)
                            .flatMap(List::stream)
                            .distinct()
                            .collect(Collectors.toList()));
                    return result;
                }).single();
    }
}
