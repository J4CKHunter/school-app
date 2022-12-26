package com.test.school.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().ignoringAntMatchers("/saveContactForm")
                .ignoringAntMatchers("/public/**")
                .and().authorizeRequests()
                .mvcMatchers("/home", "", "/").permitAll()
                .mvcMatchers("/holidays/**").permitAll()
                .mvcMatchers(HttpMethod.GET,"/contact").permitAll()
                .mvcMatchers(HttpMethod.POST,"/contact").denyAll()
                .mvcMatchers("/saveContactForm").permitAll()
                .mvcMatchers("/courses").permitAll()//.authenticated()   şifre ister
                .mvcMatchers("/about").permitAll()//.denyAll()// hepsini reddeder
                .mvcMatchers("/login").permitAll()
                .mvcMatchers("/dashboard").authenticated()
                .mvcMatchers("/displayContactForms").hasRole("ADMIN")
                .mvcMatchers("/public/**").permitAll()
                .mvcMatchers("/displayProfile").authenticated()
                .mvcMatchers("/updateProfile").authenticated()
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .mvcMatchers("/student/**").hasRole("STUDENT")
                .mvcMatchers("/school/actuator/**").hasRole("ADMIN")
                .and().formLogin().loginPage("/login")
                .defaultSuccessUrl("/dashboard").failureForwardUrl("/login?error=true").permitAll()
                .and().logout().logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true).permitAll()
                .and().httpBasic();


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
