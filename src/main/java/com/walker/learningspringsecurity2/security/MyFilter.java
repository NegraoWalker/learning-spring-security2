package com.walker.learningspringsecurity2.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class MyFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getHeader("Authorization") != null){ //Authorization é um header default do navegador
            System.out.println(request.getHeader("Authorization"));
            Authentication authentication = null;
            try {
                authentication = TokenValidation.decodetoken(request);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (authentication != null){
                SecurityContextHolder.getContext().setAuthentication(authentication); //Se o token for válido, repasso a informação para frente indicando que a mesma está autenticada
            }else {
                System.out.println("Erro no token!!");
            }
        }

        filterChain.doFilter(request,response); //Repassa a requisição do navegador para frente
    }
}
