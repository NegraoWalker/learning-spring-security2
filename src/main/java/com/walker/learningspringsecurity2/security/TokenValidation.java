package com.walker.learningspringsecurity2.security;

import com.walker.learningspringsecurity2.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;


import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.PublicKey;
import java.util.Collections;
import java.util.Date;

public class TokenValidation {
    //Constantes utilitárias:
    private static final String EMISSOR = "WalkerAPI";
    private static final String TOKEN_HEADER = "Bearer ";
    private static final String TOKEN_KEY = "0123456789-0123456789-0123456789"; //Tem que ter 32 caracteres
    private static final long ONE_SECOND = 1000;
    private static final long ONE_MINUTE = 60*ONE_SECOND;
    public static AuthToken encodeToken(User user){
        Key secretKey = Keys.hmacShaKeyFor(TOKEN_KEY.getBytes(StandardCharsets.UTF_8));
        String tokenJWT = Jwts
                .builder()
                .subject(user.getLogin())
                .issuer(EMISSOR)
                .expiration(new Date(System.currentTimeMillis() + ONE_MINUTE))
                .signWith(secretKey)
                .compact();
        return new AuthToken(TOKEN_HEADER + tokenJWT);
    }
    public static Authentication decodetoken(HttpServletRequest httpServletRequest) throws Exception{ //Validação do acesso
        String jwtToken = httpServletRequest.getHeader("Authorization");
        jwtToken = jwtToken.replace(TOKEN_HEADER,"");
        Jws<Claims> claimsJws = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(TOKEN_KEY.getBytes(StandardCharsets.UTF_8))).build().parseSignedClaims(jwtToken);

        String userLogin = claimsJws.getPayload().getSubject(); //Usuário
        System.out.println(userLogin);
        String sender = claimsJws.getPayload().getIssuer(); //Emissor
        System.out.println(sender);
        Date expirationTime = claimsJws.getPayload().getExpiration(); //Expiração do token
        System.out.println(expirationTime);

        if (userLogin.length() > 0 && sender.equals(EMISSOR) && expirationTime.after(new Date(System.currentTimeMillis()))){ //Passando o token que criei para validação
            return new UsernamePasswordAuthenticationToken("user",null, Collections.emptyList()); //Retornando que o usuário está autenticado
        }
        return null;
    }
}
