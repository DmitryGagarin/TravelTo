package com.travel.to.travel_to.security;

import com.travel.to.travel_to.entity.AuthUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JwtProvider {
    static SecretKey key = Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes());

    public static String generateToken(Authentication auth) {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles = populateAuthorities(authorities);

        Map<String, Object> authUserMap = new HashMap<>();
        authUserMap.put("uuid", ((AuthUser) auth.getPrincipal()).getUuid());
        authUserMap.put("email", ((AuthUser) auth.getPrincipal()).getEmail());
        authUserMap.put("name", ((AuthUser) auth.getPrincipal()).getName());
        authUserMap.put("surname", ((AuthUser) auth.getPrincipal()).getSurname());
        authUserMap.put("password", ((AuthUser) auth.getPrincipal()).getPassword());

        String jwt = Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + 86400000))
                .claim("auth", authUserMap) // Store the Map as "auth"
                .claim("email", auth.getName())
                .claim("authorities", roles)
                .signWith(key)
                .compact();
        System.out.println("Token for parsing in JwtProvider: " + jwt);
        return jwt;
    }
    private static String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auths = new HashSet<>();
        for(GrantedAuthority authority: authorities) {
            auths.add(authority.getAuthority());
        }
        return String.join(",",auths);
    }
}
