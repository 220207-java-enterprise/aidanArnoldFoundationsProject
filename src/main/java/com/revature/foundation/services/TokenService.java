package com.revature.foundation.services;

import com.revature.foundation.dtos.responses.Principal;
import com.revature.foundation.util.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import java.util.Date;

public class TokenService {

    private JwtConfig jwtConfig;

    public TokenService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String generateToken(Principal subject) {

        // number of milliseconds passed since the beginning of UNIX time
        // start of UNIX time: January 1, 1970
        long now = System.currentTimeMillis();

        JwtBuilder tokenBuilder = Jwts.builder()
                                      .setId(subject.getUserId())
                                      .setIssuer("foundation")
                                      .setIssuedAt(new Date(now))
                                      .setExpiration(new Date(now + jwtConfig.getExpiration()))
                                      .setSubject(subject.getUsername())
                                      .claim("roleId", subject.getRoleId())
                                      .signWith(jwtConfig.getSigAlg(), jwtConfig.getSigningKey());

        return tokenBuilder.compact();

    }

    public Principal extractRequesterDetails(String token) {

        try {

            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSigningKey())
                    .parseClaimsJws(token)
                    .getBody();

            Principal principal = new Principal();
            principal.setUserId(claims.getId());
            principal.setUsername(claims.getSubject());
            principal.setRoleId(claims.get("roleId", String.class));
//            System.out.println(claims);
            return principal;

        } catch (Exception e) {
            return null;
        }

    }
}
