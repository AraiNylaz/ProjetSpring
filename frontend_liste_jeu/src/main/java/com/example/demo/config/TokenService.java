package com.example.demo.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.model.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class TokenService {

    private CustomProperties cs;
    private final Algorithm jwtAlgorithm;
    private final JWTVerifier jwtVerifier;

    public TokenService(CustomProperties cs){
        this.cs=cs;
        jwtAlgorithm=Algorithm.HMAC256(cs.getJWTSecret());
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

    public String createToken(UserDTO user){
        String token = JWT.create().withIssuer("auth0").withClaim("user", user.getId()).sign(jwtAlgorithm);
        return token;
    }

    public int decode(String token){
        DecodedJWT tok = jwtVerifier.verify(token);
        return tok.getClaim("user").asInt();
    }
}
