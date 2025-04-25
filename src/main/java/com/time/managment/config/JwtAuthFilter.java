//package com.time.managment.config;

import com.time.managment.constants.Constants;
import com.time.managment.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

//@Component
//@RequiredArgsConstructor
//public class JwtAuthFilter extends OncePerRequestFilter {
//    private final JwtUtil jwtUtil;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String path = request.getRequestURI();
//        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//
//        boolean isInternalApi = path.startsWith("/api/info-controller") || path.startsWith("/api/weekend-controller");
//
//        if (isInternalApi) {
//            if (Objects.isNull(authHeader) || !authHeader.startsWith(Constants.Jwt.BEARER)) {
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid token");
//                return;
//            }
//
//            String token = authHeader.substring(7);
//            if (!jwtUtil.validateToken(token)) {
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
//                return;
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}
