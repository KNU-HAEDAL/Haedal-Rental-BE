package com.rental.haedal.service;

import com.rental.haedal.auth.AuthUtil;
import com.rental.haedal.domain.Member;
import com.rental.haedal.repository.MemberRepository;
import com.rental.haedal.auth.MemberUserDetails;
import com.rental.haedal.dto.auth.req.LoginRequest;
import com.rental.haedal.dto.auth.req.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final AuthUtil authUtil;

    long expireTimeMs = 1000 * 60 * 60 * 12;
    long refreshExpireTimeMs = 1000 * 60 * 60 * 24 * 14;

    // Login
    @Transactional
    public Map<String, String> login(LoginRequest request) {
        Member member = memberRepository.findByUserIdAndPassword(request.id(), request.password())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 잘못되었습니다."));

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", authUtil.createAccessToken(member.getUserId(), expireTimeMs));
        tokens.put("refreshToken", authUtil.createRefreshToken(member.getUserId(), refreshExpireTimeMs));

        return tokens;
    }

    // Sign Up
    @Transactional
    public void signup(SignUpRequest request) {
        Member member = Member.builder()
                .userId(request.id())
                .password(request.password())
                .name(request.name())
                .phoneNumber(request.phoneNumber())
                .build();

        memberRepository.save(member);
    }

    // Logout
//    public LogoutResponse logout(LogoutRequest request) {
//
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));

        return new MemberUserDetails(member);
    }
}