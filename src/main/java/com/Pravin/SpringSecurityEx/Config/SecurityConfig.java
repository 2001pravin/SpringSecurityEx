package com.Pravin.SpringSecurityEx.Config;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Autowired
   private UserDetailsService userDetailsService;

   @Autowired
   private JwtFilter JwtFilter;
@SuppressWarnings("deprecation")
@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
       return http
       .csrf(csrf -> csrf.disable()) 
       .authorizeRequests(request->request
       .requestMatchers("/register","/login")
       .permitAll()
       .anyRequest().authenticated())
       .httpBasic(Customizer.withDefaults())   
       .sessionManagement(Session->Session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
       .addFilterBefore(JwtFilter, UsernamePasswordAuthenticationFilter.class)
       
    //    http.csrf(Customizer->Customizer.disable());
    //    http.authorizeRequests(Request->Request.anyRequest().authenticated());
    //    http.formLogin(Customizer.withDefaults());
    //    http.httpBasic(Customizer.withDefaults());
    //    http.sessionManagement(Session->Session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    .httpBasic(Customizer.withDefaults())
        .build();
    }
    //  @Bean
    // public UserDetailsService  userDetailsService(){


    //         return new InMemoryUserDetailsManager();

    // }
@Bean
 AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
    return config.getAuthenticationManager();
    
}

    @Bean
    AuthenticationProvider authenticationProvider() {
         DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
         provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
         provider.setUserDetailsService(userDetailsService);
        
         return provider;
    }

}
