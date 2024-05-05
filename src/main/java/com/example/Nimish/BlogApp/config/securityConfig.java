package com.example.Nimish.BlogApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import com.example.Nimish.BlogApp.security.customUserDetailService;


@EnableWebSecurity
@Configuration
public class securityConfig   {

//    @Autowired
//    private customUserDetailService customUserDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{

        http.
                csrf(csrf ->csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/token/**").permitAll()
                        .anyRequest().authenticated()
                )
               .httpBasic(Customizer.withDefaults());

        DefaultSecurityFilterChain defaultSecurityFilterChain = http.build();
        return defaultSecurityFilterChain;

    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth)throws Exception{
//        auth.userDetailsService(this.customUserDetailService).passwordEncoder(passwordEncoder());
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

}
