package com.dorjear.study.customer.config

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.web.bind.annotation.RestController

@EnableResourceServer
@RestController //Must with @RestController here otherwise not working
class ResourceServerConfiguration : ResourceServerConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {

        http.requestMatchers().antMatchers("/customer/**").and().authorizeRequests().anyRequest().authenticated()
                .and().authorizeRequests().antMatchers("/customer/list").access("hasRole('ADMIN')")
                .and().authorizeRequests().antMatchers("/customer/show").access("hasRole('USER')")
                .and().authorizeRequests().antMatchers("/customer/create").access("hasRole('ADMIN')")
                .and().authorizeRequests().antMatchers("/customer/update").access("hasRole('USER')")
                .and().authorizeRequests().antMatchers("/customer/delete").access("hasRole('ADMIN')")
    }
}