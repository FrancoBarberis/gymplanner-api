package com.franco.gymplanner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // ❌ En dev: desactivar CSRF para permitir POST/PUT sin token
            .csrf(csrf -> csrf.disable())

            // ❌ Desactivar Basic Auth y Login Form
            .httpBasic(httpBasic -> httpBasic.disable())
            .formLogin(form -> form.disable())

            // ✔ Permitir TODO en dev (TODOS los endpoints)
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            )

            // ✔ Permitir IFRAME para H2 Console
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }
}