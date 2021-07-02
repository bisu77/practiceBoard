package com.practice.config.auth;

import com.practice.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .headers().frameOptions().disable()
//                .and()
//                    .authorizeRequests()
//                    .antMatchers("/","/css/**","/images/**","/js/**","/h2-console/**").permitAll()
////                    .antMatchers(HttpMethod.POST, "/posts", "/posts/**").hasRole(Role.USER.name())
////                    .antMatchers(HttpMethod.PUT, "/posts", "/posts/**").hasRole(Role.USER.name())
////                    .antMatchers(HttpMethod.DELETE, "/posts", "/posts/**").hasRole(Role.USER.name())
//                    .anyRequest().authenticated()
//                .and()
//                    .logout()
//                        .logoutSuccessUrl("/")
//                .and()
//                    .oauth2Login()
//                        .userInfoEndpoint()
//                            .userService(customOAuth2UserService);


        //api 송수신을 위해 security 초기인증 해제
        http
                .csrf()
                .disable();

        http
                .headers()
                .frameOptions()
                .disable();

        //oauth login & logout
        http
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);

        http
                .logout()
                .logoutSuccessUrl("/");
    }
}
