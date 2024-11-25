package com.Pravin.SpringSecurityEx.Config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.Pravin.SpringSecurityEx.Services.JwtImp;
import com.Pravin.SpringSecurityEx.Services.MyUserDataileServices;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Configuration
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtImp jwtImp;// Use consistent naming conventions

    @Autowired
    private ApplicationContext context; // Proper injection for ApplicationContext

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal( HttpServletRequest request,  HttpServletResponse response,  FilterChain filterChain)
            throws ServletException, IOException {
                

        String authHeader = request.getHeader("Authorization"); // Correct header name
        System.out.println("Authorization Header: " + authHeader);
        String token = null;
        String username = null;

        // Extract token and username
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            System.out.println(token);
            username = jwtImp.extractUSername(token);
            System.out.println(username);
        }

        // Authenticate user if valid token is present
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println(username);
            UserDetails userDetails = context.getBean(MyUserDataileServices.class).loadUserByUsername(username);
            System.out.println(userDetails);
            if (jwtImp.validateToken(token, userDetails)) {
                System.out.println("true");
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Proceed with the filter chain
        filterChain.doFilter(request, response);
    }
}
