package com.rental.haedal.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class AuthUtil {
    // JWT Access Token 발급
    public static String createAccessToken(String loginId, SecretKey key, long accessExpireTimeMs) {
        try {
            return Jwts.builder()
                    .issuer("server")
                    .claim("memberId", loginId)
                    .claim("tokenType", "access")
                    .expiration(new Date(System.currentTimeMillis() + accessExpireTimeMs))
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .signWith(key, Jwts.SIG.HS512)
                    .compact();
        } catch (JwtException e) {
            throw new RuntimeException("Access Token 생성 중 오류가 발생했습니다.", e);
        }
    }

    // JWT Refresh Token 발급
    public static String createRefreshToken(String loginId, SecretKey key, long refreshExpireTimeMs) {
        try {
            return Jwts.builder()
                    .issuer("server")
                    .claim("memberId", loginId)
                    .claim("tokenType", "refresh")
                    .expiration(new Date(System.currentTimeMillis() + refreshExpireTimeMs))
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .signWith(key, Jwts.SIG.HS512)
                    .compact();
        } catch (JwtException e) {
            throw new RuntimeException("Refresh Token 생성 중 오류가 발생했습니다.", e);
        }
    }

    // Token 의 만료시간이 유효한지 검증
    public static boolean isExpired(String token, SecretKey key) {
        try {
            Claims claims = parsingToken(token, key);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date(System.currentTimeMillis()));
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("토큰이 만료되었습니다.");
        } catch (Exception e) {
            System.out.println("Token 만료 시간 검증 에러 발생: " + e.getMessage());
            return true;
        }
    }

    // JWT type 반환
    public static String getTokenType(String token, SecretKey key) {
        try {
            Claims claims = parsingToken(token, key);
            return claims.get("tokenType", String.class);
        } catch (Exception e) {
            System.out.println("Token Type 확인 중 오류 발생: " + e.getMessage());
            return "";
        }
    }

    // JWT Token 값을 파싱
    public static Claims parsingToken(String token, SecretKey key) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // claim 으로부터 DB 의 ID 추출하기
    public static String getMemberID(String token, SecretKey key) {
        try {
            Claims claims = parsingToken(token, key);
            return claims.get("memberId", String.class);
        } catch (Exception e) {
            System.out.println("Member ID 추출 오류 발생: " + e.getMessage());
            return "";
        }
    }
}
