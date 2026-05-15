package com.example.api_gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.*;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthFilter implements GlobalFilter {

    private final JwtUtil jwtUtil;

    @Autowired
    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        // Let browser CORS preflight requests pass through the gateway.
        if (exchange.getRequest().getMethod() == HttpMethod.OPTIONS) {
            return chain.filter(exchange);
        }

        // Public APIs
        if (path.startsWith("/auth/login")
                || path.startsWith("/auth/register")
                || path.startsWith("/auth/password/forgot/")
                || path.startsWith("/flights/search")) {
            return chain.filter(exchange);
        }

        String token = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (token == null || !token.startsWith("Bearer ")) {
            return unauthorized(exchange);
        }

        if (!jwtUtil.validate(token)) {
            return unauthorized(exchange);
        }

        String role = jwtUtil.getRole(token);

        //  RBAC

        boolean staffOrAdmin = "STAFF".equals(role) || "ADMIN".equals(role);

        // ADMIN only
        if (path.startsWith("/airports") || path.startsWith("/airlines")) {
            if (!"ADMIN".equals(role)) return forbidden(exchange);
        }

        // Staff can view bookings but cannot operate on booking/user mutations.
        if (path.matches("/bookings/[^/]+/cancel")) {
            if (!("ADMIN".equals(role) || "PASSENGER".equals(role))) return forbidden(exchange);
        }

        if (path.matches("/bookings/[^/]+/status")
                || (path.matches("/bookings/[^/]+") && exchange.getRequest().getMethod() == HttpMethod.DELETE)
                || (path.matches("/auth/users/[^/]+") && exchange.getRequest().getMethod() != HttpMethod.GET)) {
            if (!"ADMIN".equals(role)) return forbidden(exchange);
        }

        // STAFF + ADMIN read-only management APIs
        if (path.startsWith("/auth/users") || path.startsWith("/bookings/all")) {
            if (!staffOrAdmin) return forbidden(exchange);
        }

        // Flight operations: STAFF can create, ADMIN can create/update/delete.
        if (path.startsWith("/flights") && exchange.getRequest().getMethod() != HttpMethod.GET) {
            if (exchange.getRequest().getMethod() == HttpMethod.POST) {
                if (!staffOrAdmin) return forbidden(exchange);
            } else {
                if (!"ADMIN".equals(role)) return forbidden(exchange);
            }
        }

        // USER + ADMIN
        if (path.startsWith("/bookings") || path.startsWith("/payments")) {
            if (!("PASSENGER".equals(role) || staffOrAdmin)) {
                return forbidden(exchange);
            }
        }

        // Inject headers
        ServerWebExchange modified = exchange.mutate()
                .request(r -> r
                        .header("X-User", jwtUtil.getUsername(token))
                        .header("X-Role", role)
                        .header("X-User-Id", String.valueOf(jwtUtil.getUserId(token)))
                ).build();

        return chain.filter(modified);
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    private Mono<Void> forbidden(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return exchange.getResponse().setComplete();
    }
}