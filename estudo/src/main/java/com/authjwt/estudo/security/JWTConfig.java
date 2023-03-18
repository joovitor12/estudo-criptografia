package com.authjwt.estudo.security;

import com.authjwt.estudo.services.UserDetailsServiceIMPL;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class JWTConfig extends AbstractSecurityWebApplicationInitializer {

    private final UserDetailsServiceIMPL userDetailsServiceIMPL;
    private final PasswordEncoder passwordEncoder;

    public JWTConfig(UserDetailsServiceIMPL userDetailsServiceIMPL, PasswordEncoder passwordEncoder) {
        this.userDetailsServiceIMPL = userDetailsServiceIMPL;
        this.passwordEncoder = passwordEncoder;
    }

    protected void configure(AuthenticationManagerBuilder auth ) throws Exception{
        auth.userDetailsService(userDetailsServiceIMPL).passwordEncoder(passwordEncoder);
    }
    /*
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST, );
    }

     */
}
