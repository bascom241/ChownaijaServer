package com.example.ChowNaijaMobile.config;

import com.example.ChowNaijaMobile.service.JwtService;
import com.example.ChowNaijaMobile.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    //1 Extract authHeader from request provided by the filter
    //2 initialize token to null
    // 3 initialize userName to null
    //4 check if authHeader exits and if it satrts with Bearer
    //5 extract token from authHeader
    // 6 get the userName from the jwtService Class
    //  run this
    //  if(userName != null && SecurityContextHolder.getContext().getAuthentication()== null){
    //
    //            UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(userName);
    //            if(jwtService.validateToken(token, userDetails)){
    //                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
    //             usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    //             SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    //            }
    //        }
    //8 return this     filterChain.doFilter(request,response);


    @Autowired
    private JwtService jwtService;

    @Autowired
    private ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        logger.info("Incoming Authorization header: " + authHeader);
        String username = null;
        String token = null;

        if(authHeader != null  && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);
        }



        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
            if (jwtService.isTokenValid(token,userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null , userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request,response);
    }
}
