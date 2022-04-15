package org.citizenGuesser;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.time.Clock;

@SpringBootApplication
@EnableWebSecurity
@PropertySource("classpath:application.properties")
public class Application {
    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    public WebSecurityConfigurerAdapter securityConfigurer() {
        return new WebSecurityConfigurerAdapter() {
            @Override
            protected void configure(final HttpSecurity http) throws Exception {
                http.authorizeRequests().anyRequest().permitAll();
                http.csrf().disable();
            }
        };
    }

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
