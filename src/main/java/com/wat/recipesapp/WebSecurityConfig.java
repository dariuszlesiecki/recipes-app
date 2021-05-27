package com.wat.recipesapp;

import com.wat.recipesapp.oauth.CustomOAuth2User;
import com.wat.recipesapp.oauth.CustomOAuth2UserService;
import com.wat.recipesapp.user.UserService;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    public WebSecurityConfig(CustomOAuth2UserService oauthUserService, UserService userService) {
        this.oauthUserService = oauthUserService;
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers("/", "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout().logoutSuccessUrl("/")
                .and()
                .formLogin().permitAll()
                .loginPage("/login").defaultSuccessUrl("/home")
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(oauthUserService)
                .and()
                .successHandler((request, response, authentication) -> {

                    CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();

                    userService.processOAuthPostLogin(oauthUser.getEmail());
                    System.out.println(oauthUser.getEmail());

                    response.sendRedirect("/home");
                });
    }

    private final CustomOAuth2UserService oauthUserService;

    private final UserService userService;
}

/*
@Configuration
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .and()
                .formLogin().loginPage("/login")
                .permitAll();

    }



}*/
