package com.authjwt.estudo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.authjwt.estudo.data.UserDetailsData;
import com.authjwt.estudo.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthFilter extends UsernamePasswordAuthenticationFilter {

    public static final int TOKEN_EXPIRATION = 600_000;

    public static final String TOKEN_PASSWORD = "1d5fe6f9-d0cb-4c07-9924-c619c861105e";

    private final AuthenticationManager authenticationManager;

    public JWTAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try{
            User user = new ObjectMapper()
                    .readValue(request.getInputStream(), User.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    user.getPassword(),
                    new ArrayList<>()
            ));

        }catch(IOException e) {
            throw new RuntimeException("Failed to auth user", e);
        }



    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        UserDetailsData userDetailsData = (UserDetailsData) authResult.getPrincipal();

        String token = JWT.create().withSubject(userDetailsData.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC512(TOKEN_PASSWORD));

        response.getWriter().write(token);
        response.getWriter().flush();
    }
}
