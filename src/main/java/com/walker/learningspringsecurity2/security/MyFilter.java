package com.walker.learningspringsecurity2.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class MyFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("DEBUG: REQUISIÇÃO DO NAVEGADOR PASSOU PELO FILTRO!!");
        filterChain.doFilter(request,response); //Repassa a requisição do navegador para frente
    }
}
