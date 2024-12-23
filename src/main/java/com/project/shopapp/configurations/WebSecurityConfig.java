package com.project.shopapp.configurations;


import com.project.shopapp.filters.JwtTokenFilter;
import com.project.shopapp.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.http.HttpMethod.*;

@Configuration
//@EnableMethodSecurity
@EnableWebSecurity
@EnableWebMvc
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;

    @Value("${api.prefix}")
    private String apiPrefix;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)  throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    requests
                            .requestMatchers(
                                    String.format("%s/users/register", apiPrefix),
                                    String.format("%s/users/login", apiPrefix),
                                    String.format("%s/payment_return", apiPrefix),
                                    String.format("%s/refreshToken",apiPrefix)
                            )
                            .permitAll()
                            .requestMatchers(POST,
                                    String.format("%s/createBook/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.AUTHOR)
                            .requestMatchers(POST,
                                    String.format("%s/acceptBook/**", apiPrefix)).hasAnyRole(Role.ADMIN)
                            .requestMatchers(POST,
                                    String.format("%s/denyBook/**", apiPrefix)).hasAnyRole(Role.ADMIN)
                            .requestMatchers(GET,
                                    String.format("%s/findBookBought/**", apiPrefix)).hasAnyRole(Role.USER, Role.AUTHOR)
                            .requestMatchers(GET,
                                    String.format("%s/findBookAuthor/**", apiPrefix)).hasAnyRole(Role.USER, Role.AUTHOR)

                            .requestMatchers(POST,
                                    String.format("%s/payment/**", apiPrefix)).hasAnyRole(Role.USER, Role.AUTHOR)
                            .requestMatchers(GET,
                                    String.format("%s/payment/**", apiPrefix)).hasAnyRole(Role.USER, Role.AUTHOR)
                            .requestMatchers(POST,
                                    String.format("%s/list/**", apiPrefix)).hasAnyRole(Role.USER, Role.AUTHOR)

                            .requestMatchers(DELETE,
                                    String.format("%s/list/**", apiPrefix)).hasAnyRole(Role.USER, Role.AUTHOR)

                            .requestMatchers(GET,
                                    String.format("%s/list/**", apiPrefix)).hasAnyRole(Role.USER, Role.AUTHOR)

                            .requestMatchers(GET,
                                    String.format("%s/view_advertisement", apiPrefix)).hasAnyRole(Role.USER, Role.AUTHOR)

                            .requestMatchers(POST,
                                    String.format("%s/review/**", apiPrefix)).hasAnyRole(Role.USER, Role.AUTHOR)
                            .requestMatchers(GET,
                                    String.format("%s/review/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.AUTHOR)
                            .requestMatchers(PUT,
                                    String.format("%s/review/**", apiPrefix)).hasAnyRole(Role.USER, Role.AUTHOR)
                            .
                            requestMatchers(POST,
                                    String.format("%s/bookmark/**", apiPrefix)).hasAnyRole(Role.USER, Role.AUTHOR)

                            .requestMatchers(DELETE,
                                    String.format("%s/bookmark/**", apiPrefix)).hasAnyRole(Role.USER, Role.AUTHOR)
                            .requestMatchers(GET,
                                    String.format("%s/bookmark/**", apiPrefix)).hasAnyRole(Role.USER, Role.AUTHOR)

                            .requestMatchers(POST,
                                    String.format("%s/report/**", apiPrefix)).hasAnyRole(Role.USER, Role.AUTHOR)

                            .requestMatchers(DELETE,
                                    String.format("%s/report/**", apiPrefix)).hasAnyRole(Role.USER, Role.AUTHOR)
                            .requestMatchers(GET,
                                    String.format("%s/report/**", apiPrefix)).hasAnyRole(Role.ADMIN)

                            .requestMatchers(GET,
                                    String.format("%s/findBookWritten", apiPrefix)).hasAnyRole(Role.AUTHOR, Role.USER, Role.ADMIN)

                            .requestMatchers(POST,
                                    String.format("%s/submitOrder", apiPrefix)).hasAnyRole(Role.USER, Role.AUTHOR)

                            .requestMatchers(POST,
                                    String.format("%s/users/**", apiPrefix)).hasAnyRole(Role.USER, Role.AUTHOR)

                            .requestMatchers(GET,
                                    String.format("%s/users/**", apiPrefix)).hasAnyRole(Role.USER, Role.AUTHOR)

                            .requestMatchers(GET,
                                    String.format("%s/author/info", apiPrefix)).hasAnyRole(Role.ADMIN, Role.USER, Role.AUTHOR)

                            .requestMatchers(GET,
                                    String.format("%s/author/getAuthorRequest", apiPrefix)).hasAnyRole(Role.ADMIN)

                            .requestMatchers(POST,
                                    String.format("%s/author/become", apiPrefix)).hasAnyRole(Role.USER)

                            .requestMatchers(GET,
                                    String.format("%s/author/**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN, Role.AUTHOR)

                            .requestMatchers(GET,
                                    String.format("%s/categories**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN, Role.AUTHOR)

                            .requestMatchers(POST,
                                    String.format("%s/categories/**", apiPrefix)).hasAnyRole(Role.ADMIN)

                            .requestMatchers(PUT,
                                    String.format("%s/categories/**", apiPrefix)).hasAnyRole(Role.ADMIN)

                            .requestMatchers(DELETE,
                                    String.format("%s/categories/**", apiPrefix)).hasAnyRole(Role.ADMIN)

                            .anyRequest().authenticated();

                });
        return http.build();
    }
}
