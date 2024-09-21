package com.pjs.golf_webflex.app.auth.adapter;

import com.pjs.golf_webflex.app.auth.dto.Account;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class AccountAdapter {

    public static UserDetails convertToUserDetails(Account account) {
        return User.withUsername(account.getUsername())
                .password(account.getPassword())
                .roles(account.getRoles().toArray(new String[0]))
                .build();
    }
}
