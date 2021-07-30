package com.liu.blog.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class ResourceServerConfig {
    // @formatter:off
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeExchange()
       http.antMatcher("/**").authorizeRequests().mvcMatchers("/**").permitAll();
//        http
//                .mvcMatcher("/messages/**")
//                .authorizeRequests()
//                .mvcMatchers("/messages/**").access("hasAuthority('SCOPE_message.read')")
//                .and()
//                .oauth2ResourceServer()
//                .jwt();
        return http.build();
    }
    // @formatter:on
}
