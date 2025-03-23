package com.rental.haedal.auth.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {


    private final AuthService authService;

    @Value("${jwt.secret}")
    private String key;

    private SecretKey secretKey;

    // SecretKey 초기화
    @Override
    protected void initFilterBean() {
        byte[] decodedKey = Base64.getDecoder().decode(key);
        this.secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");
    }


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
        if (AuthUtil.isExpired(token, secretKey)) {
            filterChain.doFilter(request, response);
            return;
        }

//        // 토큰에서 사용자 정보 추출
//        Claims claims = AuthUtil.extractClaims(token, key);
//        String username = claims.getSubject();
//
//        // SecurityContext에 인증 정보가 없는 경우에만 설정
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            // UserDetails 로드
//            UserDetails userDetails = authService.loadUserByUsername(username);
//
//            // 추가적인 토큰 검증이 필요한 경우 여기서 수행
//            if (AuthUtil.validateToken(token, userDetails, key)) {
//                // 인증 객체 생성 및 SecurityContext에 설정
//                UsernamePasswordAuthenticationToken authToken =
//                        new UsernamePasswordAuthenticationToken(
//                                userDetails,
//                                null, // credentials (비밀번호)는 이미 검증되었으므로 null
//                                userDetails.getAuthorities()
//                        );
//
//                // 요청 세부 정보 설정 (선택사항)
//                // authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                // SecurityContext에 인증 객체 설정
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }
}