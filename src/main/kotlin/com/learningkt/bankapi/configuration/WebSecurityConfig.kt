package com.learningkt.bankapi.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@Configuration
@EnableWebSecurity
class WebSecurityConfig: WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.httpBasic()
    }

    @Bean
    override fun userDetailsService(): UserDetailsService {
        val pedro = User.withDefaultPasswordEncoder()
            .username("Pedro")
            .password("123")
            .roles("USER")
            .build()
        val admin = User.withDefaultPasswordEncoder()
            .username("Admin")
            .password("Admin")
            .roles("ADMIN")
            .build()

        return InMemoryUserDetailsManager(pedro, admin)
    }
}