package com.finalProject.digitalLibrary.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/reader/*").permitAll()
                .antMatchers(HttpMethod.PUT,"/reader/*").permitAll()
                .antMatchers(HttpMethod.PATCH,"/reader/*").permitAll()
                .antMatchers(HttpMethod.POST,"/reader/*").permitAll()
                .antMatchers(HttpMethod.DELETE,"/reader/*").permitAll()

                .antMatchers(HttpMethod.GET,"/info/*").permitAll()
                .antMatchers(HttpMethod.PUT,"/info/*").permitAll()
                .antMatchers(HttpMethod.PATCH,"/info/*").permitAll()
                .antMatchers(HttpMethod.POST,"/info/*").permitAll()
                .antMatchers(HttpMethod.DELETE,"/info/*").permitAll()

                .antMatchers(HttpMethod.GET,"/author/*").permitAll()
                .antMatchers(HttpMethod.PUT,"/author/*").permitAll()
                .antMatchers(HttpMethod.PATCH,"/author/*").permitAll()
                .antMatchers(HttpMethod.POST,"/author/*").permitAll()
                .antMatchers(HttpMethod.DELETE,"/author/*").permitAll()

                .antMatchers(HttpMethod.GET,"/admin/*").permitAll()
                .antMatchers(HttpMethod.PUT,"/admin/*").permitAll()
                .antMatchers(HttpMethod.PATCH,"/admin/*").permitAll()
                .antMatchers(HttpMethod.POST,"/admin/*").permitAll()
                .antMatchers(HttpMethod.DELETE,"/admin/*").permitAll()

                .antMatchers("/info/*").hasAnyRole("READER","AUTHOR","ADMIN")
                .antMatchers("/reader/*").hasAnyRole("READER")
                .antMatchers("/author/*").hasAnyRole("AUTHOR")
                .antMatchers("/admin/*").hasAnyRole("ADMIN")
                .and()
                .httpBasic();
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();

        UserDetails user1 = User.withUsername("Oscar Wilde").password("333").roles("AUTHOR").build();
        UserDetails user2 = User.withUsername("Nicholas Sparks").password("444").roles("AUTHOR").build();
        UserDetails user3 = User.withUsername("Mark Ciceron").password("555").roles("AUTHOR").build();

        UserDetails user4 =  User.withUsername("Ivailo Vatev").password("222").roles("READER").build();
        UserDetails user6 =  User.withUsername("Anton Almishev").password("345").roles("READER").build();
        UserDetails user7 =  User.withUsername("Vaska Milenkova").password("777").roles("READER").build();

        UserDetails user8 =  User.withUsername("Yoanna Yonova").password("666").roles("ADMIN").build();

        userDetailsService.createUser(user1);
        userDetailsService.createUser(user2);
        userDetailsService.createUser(user3);
        userDetailsService.createUser(user4);
        userDetailsService.createUser(user6);
        userDetailsService.createUser(user7);
        userDetailsService.createUser(user8);

        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){

        return NoOpPasswordEncoder.getInstance();
    }



}

