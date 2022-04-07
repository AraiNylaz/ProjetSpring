package com.example.gateway.Filtre;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;


@Component
public class AuthorizeFilter extends AbstractGatewayFilterFactory<AuthorizeFilter.Config> {

    public AuthorizeFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange,chain) ->{
            HttpHeaders headers=exchange.getRequest().getHeaders();
            headers.forEach((Object,value) -> System.out.println(Object + "and " +value ));
            if(!headers.containsKey("Authorization") || !config.verifyToken(headers.getFirst("Authorization"))){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
            System.out.println("thanks");
            return chain.filter(exchange).then();
        };
    }

    public static class Config{

        private final Algorithm jwtAlgorithm;
        private final JWTVerifier jwtVerifier;
        private String JWTSecret="RandomCode";

        public Config(){
            jwtAlgorithm=Algorithm.HMAC256(JWTSecret);
            jwtVerifier = JWT.require(this.jwtAlgorithm).withIssuer("auth0").build();
        }

        public boolean verifyToken(String token){
            try {
                jwtVerifier.verify(token);
            } catch (Exception e) {
                return false;
            }
            return true;
        }
    }
}
