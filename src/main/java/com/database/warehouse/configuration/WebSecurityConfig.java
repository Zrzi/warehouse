package com.database.warehouse.configuration;

import com.database.warehouse.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private EmployeeService employeeService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(employeeService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/static/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/static/templates/login.html")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/static/templates/index.html")
                .failureUrl("/static/templates/login.html");
        http.logout()
                .logoutUrl("/logout").permitAll()
                .deleteCookies("JSESSIONID");
        http.authorizeRequests()
                .antMatchers("/static/**/**", "/login").permitAll()
                .anyRequest().authenticated();
        http.csrf()
                .disable();
        /*http.authorizeRequests()
                .antMatchers("/templates/**" ,"/js/**", "/css/**", "/images/**", "/fonts/**", "/lib/** ", "/login").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/templates/login.html")
                .successHandler(new AuthenticationSuccessHandler() {
                    private final RequestCache requestCache = new HttpSessionRequestCache();
                    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        SavedRequest savedRequest = requestCache.getRequest(httpServletRequest, httpServletResponse);
                        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, savedRequest.getRedirectUrl());
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/templates/login.html");
                    }
                })
                .loginProcessingUrl("/login")
                .and()
            .csrf()
                .disable();*/
    }

}
