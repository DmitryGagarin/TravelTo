package com.travel.to.travel_to.security.jwt;

import com.travel.to.travel_to.entity.user.AuthUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.jetbrains.annotations.NotNull;
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
    static final SecretKey key = Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes());

    public static String generateAccessToken(Authentication auth) {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles = populateAuthorities(authorities);

        Map<String, Object> authUserMap = extractAuthUserFromToken(auth, roles);

        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + 1800000)) // 30 minutes
                .claim("auth", authUserMap)
                .claim("email", auth.getName())
                .claim("authorities", roles)
                .signWith(key)
                .compact();
    }

    public static String generateRefreshToken(Authentication auth) {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles = populateAuthorities(authorities);

        Map<String, Object> authUserMap = new HashMap<>();
        authUserMap.put("uuid", ((AuthUser) auth.getPrincipal()).getUuid());
        authUserMap.put("email", ((AuthUser) auth.getPrincipal()).getEmail());
        authUserMap.put("password", ((AuthUser) auth.getPrincipal()).getPassword());

        return Jwts.builder()
            .issuedAt(new Date())
            .expiration(new Date(new Date().getTime() + 604800000)) // 7 days
            .claim("auth", authUserMap)
            .claim("email", auth.getName())
            .claim("authorities", roles)
            .signWith(key)
            .compact();
    }

    @NotNull
    private static Map<String, Object> extractAuthUserFromToken(Authentication auth, String roles) {
        Map<String, Object> authUserMap = new HashMap<>();
        authUserMap.put("uuid", ((AuthUser) auth.getPrincipal()).getUuid());
        authUserMap.put("email", ((AuthUser) auth.getPrincipal()).getEmail());
        authUserMap.put("name", ((AuthUser) auth.getPrincipal()).getName());
        authUserMap.put("surname", ((AuthUser) auth.getPrincipal()).getSurname());
        authUserMap.put("password", ((AuthUser) auth.getPrincipal()).getPassword());
        authUserMap.put("verified", ((AuthUser) auth.getPrincipal()).getVerified());
        authUserMap.put("roles", roles);
        return authUserMap;
    }


    private static String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auths = new HashSet<>();
        for(GrantedAuthority authority: authorities) {
            auths.add(authority.getAuthority());
        }
        return String.join(",",auths);
    }
}
