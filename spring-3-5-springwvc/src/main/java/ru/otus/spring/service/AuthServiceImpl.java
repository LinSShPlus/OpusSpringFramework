package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.spring.domain.AuthResponse;
import ru.otus.spring.domain.User;

import java.time.Instant;
import java.util.stream.Collectors;

/**
 * AuthServiceImpl
 **/
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public AuthResponse getToken(@RequestBody User user) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getLogin());
        if (!userDetails.isEnabled())
            throw new DisabledException("User disabled");
        if (passwordEncoder.matches(user.getPassword(), userDetails.getPassword())) {
            String token = generateToken(userDetails);
            return AuthResponse
                    .builder()
                    .tokenType(AuthResponse.TOKEN_TYPE_BEARER)
                    .token(token)
                    .build();
        } else {
            throw new BadCredentialsException("Invalid password");
        }
    }

    /**
     * Generates the JWT token with claims
     *
     * @param userDetails The user details
     * @return Returns the JWT token
     */
    private String generateToken(UserDetails userDetails) {
        Instant now = Instant.now();
        long expiry = 36000L;
        // @formatter:off
        String scope = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(userDetails.getUsername())
                .claim("scope", scope)
                .build();
        // @formatter:on
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}
