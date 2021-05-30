package com.wat.recipesapp;

import com.wat.recipesapp.oauth.CustomOAuth2UserService;
import com.wat.recipesapp.user.CustomUserDetailsService;
import com.wat.recipesapp.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;


@Configuration
@EnableWebSecurity
@EnableJpaRepositories
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService oauthUserService;

    private final UserService userService;


    public WebSecurityConfig(CustomOAuth2UserService oauthUserService, UserService userService) {
        this.oauthUserService = oauthUserService;
        this.userService = userService;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers("/", "/login","/user/register","/resources/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .defaultSuccessUrl("/home")
                //.successForwardUrl("/home")
                .and()
                .logout().logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(oauthUserService)
                .and()
                .successHandler((request, response, authentication) -> {

                    OAuth2User u = (OAuth2User) authentication.getPrincipal();
                    String email = u.getAttribute("email");
                    System.out.println(u.getAttributes());
                    System.out.println(email);
                    userService.processOAuthPostLogin(email);

                    response.sendRedirect("/home");
                });
    }


}
