package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.AuthError;
import ru.otus.spring.domain.AuthResponse;
import ru.otus.spring.domain.User;
import ru.otus.spring.service.AuthService;

/**
 * AuthController
 **/
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/token")
    public ResponseEntity<AuthResponse> token(@RequestBody User user) {
        final AuthResponse token = authService.getToken(user);
        return ResponseEntity.ok(token);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<AuthError> handleException(AuthenticationException e) {
        AuthError authError = AuthError.builder().error(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authError);
    }

}
