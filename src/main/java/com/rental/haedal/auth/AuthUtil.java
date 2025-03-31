package com.rental.haedal.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class AuthUtil {

    private final SecretKey key;

    // 생성자에서 secretKey를 가져와서 key에 할당
    public AuthUtil(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));  // secret을 사용하여 key 생성
    }

    // claim 으로부터 DB 의 ID 추출하기
    public String getMemberID(String token) {
        try {
            Claims claims = parsingToken(token);
            return claims.get("memberId", String.class);
        } catch (Exception e) {
            System.out.println("Member ID 추출 오류 발생: " + e.getMessage());
            return "";
        }
    }

    // JWT Access Token 발급
    public String createAccessToken(String userId, long accessExpireTimeMs) {
        try {
            return Jwts.builder()
                    .issuer("server")
                    .claim("userId", userId)
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
    public String createRefreshToken(String userId, long refreshExpireTimeMs) {
        try {
            return Jwts.builder()
                    .issuer("server")
                    .claim("userId", userId)
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
    public boolean isExpired(String token) {
        try {
            Claims claims = parsingToken(token);
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
    public String getTokenType(String token) {
        try {
            Claims claims = parsingToken(token);
            return claims.get("tokenType", String.class);
        } catch (Exception e) {
            System.out.println("Token Type 확인 중 오류 발생: " + e.getMessage());
            return "";
        }
    }

    // JWT Token 값을 파싱
    public Claims parsingToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}