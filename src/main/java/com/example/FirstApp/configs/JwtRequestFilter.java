package com.example.FirstApp.configs;

import com.example.FirstApp.utils.JwtTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.stream.Collectors;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    final JwtTokenUtils jwtToken;
    public JwtRequestFilter(JwtTokenUtils jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
        String header = request.getHeader("Authorization");
        String username=null;
        String jwt=null;
        if(header!= null && header.startsWith("Bearer")){
            jwt = header.substring(7);
            try{
                username = jwtToken.getUsername(jwt);
            }
            catch (ExpiredJwtException e){
                logger.debug("вреям вышло");
            }
            catch (SignatureException e){
                logger.debug("неправильная сигнатура");
            }
        }
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    jwtToken.getRoles(jwt).stream().map(SimpleGrantedAuthority::new ).collect(Collectors.toList())
            );
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        filterChain.doFilter(request,response);
    }
}
