package com.auth_service.config;

import com.auth_service.service.CustomUserDetailsService;
import com.auth_service.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private CustomUserDetailsService customUserDetailsService;

    public JwtFilter(JWTService jwtService, CustomUserDetailsService customUserDetailsService) {
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {




        String path = request.getRequestURI();
        System.out.println("Request URI" + path);


        String authHeader = request.getHeader("Authorization");
        System.out.println(authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7).replace("\"", "").trim();
            System.out.println(jwt);

            String email = jwtService.validateTokenAndRetrieveSubject(jwt);
            System.out.println(email);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                var userDetails = customUserDetailsService.loadUserByUsername(email);
                var authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);

    }
}
