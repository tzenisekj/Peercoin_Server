package com.peercoin.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;


//useful links: https://stackoverflow.com/questions/55346732/spring-security-rest-login
//              https://www.javadevjournal.com/spring/securing-a-restful-web-service-with-spring-security/
@Configuration
@EnableWebSecurity
@EnableScheduling
@SuppressWarnings("unused")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;

    AuthenticationProvider provider;

    public WebSecurityConfig(final AuthenticationProvider authenticationProvider) {
        super();
        this.provider = authenticationProvider;
    }

    private static final RequestMatcher PROTECTED_URLS = new OrRequestMatcher(
            new AntPathRequestMatcher("/api/**"),new AntPathRequestMatcher("/admin/**")
    );

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(provider);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .and()
                .authenticationProvider(provider)
                .addFilterBefore(authenticationFilter(), AnonymousAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/","/login*","/register*", "/token*", "/info").permitAll()
                .antMatchers("/api/**").authenticated()
                .antMatchers("/admin/**").hasAuthority("ROLE_admin")
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable();

    }

    @Bean
    AuthenticationFilter authenticationFilter() throws Exception {
        final AuthenticationFilter filter = new AuthenticationFilter(PROTECTED_URLS);
        filter.setAuthenticationManager(authenticationManager());
        //filter.setAuthenticationSuccessHandler(successHandler());
        return filter;
    }

    @Bean
    AuthenticationEntryPoint forbiddenEntryPoint() {
        return new HttpStatusEntryPoint(HttpStatus.FORBIDDEN);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
