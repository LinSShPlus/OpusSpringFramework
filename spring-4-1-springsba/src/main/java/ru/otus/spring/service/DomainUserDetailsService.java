package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * DomainUserDetailsService
 **/
@RequiredArgsConstructor
@Service
public class DomainUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ru.otus.spring.domain.User user = userService.getByLogin(username);

        if (user == null)
            throw new UsernameNotFoundException(String.format("Unknown user: '%s'", username));

        return User
                .withUsername(username)
                .password(user.getPassword())
                .roles(user.getRole())
                .disabled(!user.isEnabled())
                .build();
    }

}
