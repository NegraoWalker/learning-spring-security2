package com.walker.learningspringsecurity2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity //Ao adicionar a anotação @EnableWebSecurity a uma classe de configuração, você está indicando ao Spring que deseja personalizar a configuração de segurança para a sua aplicação.
public class MySecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{ //Método usado quem é liberado, quem é barrado e qual o filtro que vai ser usado
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())//Forma padrão de tratamento de autenticação do Spring Security - Desabilitado pois vamos implementar a nossa(personalizada)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry //Define as regras de autorização para diferentes tipos de solicitações HTTP(quem é permitido sem autenticação e quem não é)
                    .requestMatchers(HttpMethod.GET,"/free").permitAll() //Especificando a URL com endpoint liberada sem a necessidade de autenticação
                    .anyRequest().authenticated()) //Todas as outras URLS com endpoints terão a necessidade de autenticação para o acesso
                .addFilterBefore(new MyFilter(), UsernamePasswordAuthenticationFilter.class); //Adicionando o meu filtro para receber as requisições HTTP - Filtrar e Checar
        return httpSecurity.build();
    }
}
