package com.example.OnlyGuitars.config;

import com.example.OnlyGuitars.filter.JwtRequestFilter;
import com.example.OnlyGuitars.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    JwtService jwtService;

    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled from profiles where username=?")
                .authoritiesByUsernameQuery("select username, authority from authorities where username=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/register").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/image/{id}/download").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/profiles/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/guitars").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/guitars/{id}/image").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/guitars/create").hasAuthority("ADMIN")
                .antMatchers("/guitars/{id}/delete}").hasAuthority("ADMIN")
                .antMatchers("/requests").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/requests/get").hasAuthority("ADMIN")
                .antMatchers("/requests/{id}").hasAuthority("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .cors()
                .and()
                .addFilterBefore(new JwtRequestFilter(jwtService, userDetailsService()), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
    }
}




