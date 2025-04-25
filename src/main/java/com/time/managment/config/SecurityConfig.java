package com.time.managment.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(Customizer.withDefaults()) // защита от CSRF
//                .authorizeHttpRequests(auth -> auth
////                        .requestMatchers("/register", "/login").permitAll()  // разрешаем только для публичных
////                        .requestMatchers("/api/internal-auth/**").permitAll()  // убираем доступ к generate
////                        .requestMatchers("/api/**").authenticated()  // все /api/** защищены
//                        .anyRequest().permitAll() // остальные тоже защищены
//                );  // добавляем фильтр

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
