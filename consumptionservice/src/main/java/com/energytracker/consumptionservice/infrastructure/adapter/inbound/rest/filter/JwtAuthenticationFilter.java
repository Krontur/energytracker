package com.energytracker.consumptionservice.infrastructure.adapter.inbound.rest.filter;

import com.energytracker.consumptionservice.application.port.inbound.JwtManageUseCase;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtManageUseCase jwtManageUseCase;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        log.info("JwtAuthenticationFilter");
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Authorization header is missing or invalid");
            chain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        log.info("Token: {}", jwt);

        if (!jwtManageUseCase.validateToken(jwt)) {
            log.warn("Invalid token");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }

        String username = jwtManageUseCase.extractEmail(jwt);
        String role = jwtManageUseCase.extractRole(jwt);
        boolean isEnabled = jwtManageUseCase.isEnabled(jwt);

        log.info("Username: {}", username);
        log.info("Role: {}", role);
        log.info("isEnabled: {}", isEnabled);

        if (!isEnabled) {
            log.warn("User account is disabled: {}", username);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "User account is disabled.");
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            List<SimpleGrantedAuthority> authorities = role != null
                    ? List.of(new SimpleGrantedAuthority("ROLE_" + role))
                    : Collections.emptyList();

            var authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    authorities
            );
            log.info("User authenticated: {}", authentication);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-resources") ||
                path.startsWith("/webjars");
    }

}
