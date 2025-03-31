package com.rental.haedal.auth;

import com.rental.haedal.service.AuthService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {
    private final AuthUtil authUtil;

    private final AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Header에서 Authorization 관련 내용 가져오기
        String requestURI = request.getRequestURI();

        // /api/auth/** 경로는 JWT 검증을 건너뜀
        if (requestURI.startsWith("/api/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Header의 Authorization 값이 비어있는 경우 -> login 하지 않음
        if (authorizationHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Header의 Authorization 값이 Bearer 로 시작하지 않는 경우 -> 잘못된 토큰
        if (!authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 전송 받은 값에서 Bearer 뒤의 JWT Token 값 추출
        String token = authorizationHeader.split(" ")[1];

        // 전송 받은 token 값의 유효성 검증
        if (authUtil.isExpired(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰에서 사용자 정보 추출
        Claims claims = authUtil.parsingToken(token);
        String userId = claims.get("userId", String.class);

        // SecurityContext에 인증 정보가 없는 경우에만 설정
        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // UserDetails 로드
            UserDetails userDetails = authService.loadUserByUsername(userId);

            // 인증 객체 생성 및 SecurityContext에 설정
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null, // credentials (비밀번호)는 이미 검증되었으므로 null
                            userDetails.getAuthorities()
                    );

            // 요청 세부 정보 설정 (선택사항)
            // authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // SecurityContext에 인증 객체 설정
            SecurityContextHolder.getContext().setAuthentication(authToken);

        }

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }
}