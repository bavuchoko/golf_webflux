package com.pjs.golf_webflex.common.adapter;

import com.pjs.golf_webflex.app.auth.domain.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AccountAdapter extends User{


    private Account account;

    public AccountAdapter(Account account) {
        super(account.getUsername(), account.getPassword(), authorities(account.getRoles()));
        this.account =account;
    }


    private static Collection<? extends GrantedAuthority> authorities(List<String> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                .collect(Collectors.toSet());
    }

    public Account getAccount() {
        account.setPassword(null);
        return account;
    }
}
