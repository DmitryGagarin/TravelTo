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
import jakarta.validation.constraints.NotNull;
import org.apache.hc.core5.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JwtTokenValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
        @NotNull HttpServletRequest request,
        @NotNull HttpServletResponse response,
        @NotNull FilterChain filterChain
    ) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        if (
            requestURI.startsWith(URLConstants.SIGNIN)
            || requestURI.startsWith(URLConstants.SIGNUP)
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

                Map<?, ?> authUserMap = (Map<?, ?>) claims.get("auth");

                AuthUser authUser = new AuthUser();
                authUser.setUuid(String.valueOf(authUserMap.get("uuid")));
                authUser.setEmail(String.valueOf(authUserMap.get("email")));
                authUser.setPassword(String.valueOf(authUserMap.get("password")));

                String authorities = String.valueOf(claims.get("authorities"));
                List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authUser.getEmail(),
                    authUser.getPassword(),
                    auth
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (ExpiredJwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setHeader("Location", "http://localhost:3000/"); // Redirect to your login page (UI)
                return;
            } catch (Exception e) {
                throw new BadCredentialsException("Invalid token", e);
            }
        }

        filterChain.doFilter(request, response);
    }
}
