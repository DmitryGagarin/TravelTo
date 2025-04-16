package com.travel.to.travel_to.security.jwt;

import com.travel.to.travel_to.constants.URLConstants;
import com.travel.to.travel_to.entity.user.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.hc.core5.http.HttpHeaders;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class JwtTokenValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
        @NotNull HttpServletRequest request,
        @NotNull HttpServletResponse response,
        @NotNull FilterChain filterChain
    ) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        if (
            requestURI.equals(URLConstants.SIGNIN)
            || requestURI.equals(URLConstants.SIGNUP)
            || requestURI.equals(URLConstants.LOGOUT)
            || requestURI.equals(URLConstants.RESET_PASSWORD)
            || requestURI.startsWith(URLConstants.ACCOUNT_VERIFICATION)
            || requestURI.equals(URLConstants.ATTRACTIONS)
            || requestURI.startsWith(URLConstants.SWAGGER)
            || requestURI.startsWith(URLConstants.SWAGGER2)
            || requestURI.startsWith(URLConstants.ACTUATOR)
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.nonNull(accessToken) && accessToken.startsWith(JwtConstants.TOKEN_PREFIX)) {
            accessToken = accessToken.substring(7);
            try {
                SecretKey key = Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes());

                Claims claims = Jwts
                    .parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(accessToken)
                    .getPayload();

                Map<String, Object> authUserMap = (Map<String, Object>) claims.get("auth");

                AuthUser authUser = new AuthUser();
                authUser
                    .setUuid(String.valueOf(authUserMap.get("uuid")))
                    .setEmail(String.valueOf(authUserMap.get("email")))
                    .setName((String) authUserMap.get("name"))
                    .setSurname((String) authUserMap.get("surname"))
                    .setPassword(String.valueOf(authUserMap.get("password")))
                    .setVerified((Boolean) authUserMap.get("verified"));

//                // TODO: проверка на подтверждение
//                if (authUser.getVerified() == Boolean.FALSE) {
//                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                    response.setHeader("Location", "http://localhost:4000/");
//                    return;
//                }


                String authorities = String.valueOf(authUserMap.get("roles"));
                List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authUser,
                    authUser.getPassword(),
                    auth
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (ExpiredJwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // return 401 status code
                response.setHeader("Location", "http://localhost:4000/");
                return;
            } catch (Exception e) {
                throw new BadCredentialsException("Invalid token", e);
            }
        }

        filterChain.doFilter(request, response);
    }

    private static String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auths = new HashSet<>();
        for (GrantedAuthority authority : authorities) {
            auths.add(authority.getAuthority());
        }
        return String.join(",", auths);
    }
}
