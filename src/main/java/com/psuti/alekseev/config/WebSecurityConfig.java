package com.psuti.alekseev.config;

import com.psuti.alekseev.entity.point.JwtAuthenticationEntryPoint;
import com.psuti.alekseev.filter.JwtRequestFilter;
import com.psuti.alekseev.impl.SuccessLogoutHandlerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig {
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtRequestFilter jwtRequestFilter;
    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService,
                             JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                             JwtRequestFilter jwtRequestFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtRequestFilter = jwtRequestFilter;
    }
    @Bean
    SecurityFilterChain web(HttpSecurity http, SuccessLogoutHandlerImpl successLogoutHandler) throws Exception {
        http
                .csrf()
                .disable()
                /*.authorizeHttpRequests(
                        (authorize) -> authorize
                                .antMatchers("/auth/login", "/auth/reg")
                                .permitAll()
                                .anyRequest().authenticated()
                )*/
                /*.authorizeHttpRequests(requests -> requests
                        .requestMatchers("/auth/login", "/auth/reg").hasAnyRole("SCHUELER", "LEHRER", "VERWALTUNG")
                        .anyRequest().authenticated()
                )*/
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/auth/login", "/auth/reg")
                                .permitAll()
                                .anyRequest().authenticated()
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/auth/logout")
                                .deleteCookies("JSESSION")
                                .invalidateHttpSession(true)
                                .logoutSuccessHandler(successLogoutHandler)
                )
                .userDetailsService(userDetailsService)
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

