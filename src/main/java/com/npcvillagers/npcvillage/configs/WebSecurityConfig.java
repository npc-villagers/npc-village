package com.npcvillagers.npcvillage.configs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private AppUserDetailServiceImplementation appUserDetailService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Bean
    protected SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/signup").permitAll()
                        .requestMatchers("/css/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/",true)
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .and()
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(appUserDetailService)
                .passwordEncoder(passwordEncoder());

        return http.build();
    }
}
