package com.walker.learningspringsecurity2.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Collections;

public class TokenValidation {
    public static Authentication decodetoken(HttpServletRequest httpServletRequest) throws Exception{ //Validação do acesso
        if (httpServletRequest.getHeader("Authorization").equals("Bearer *token-walker-123456")){ //Passando o token que criei para validação
            return new UsernamePasswordAuthenticationToken("user",null, Collections.emptyList()); //Retornando que o usuário está autenticado
        }
        return null;
    }
}
