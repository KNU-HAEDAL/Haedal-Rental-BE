package com.rental.haedal.auth.service;

import com.rental.haedal.auth.domain.Member;
import com.rental.haedal.auth.domain.MemberRepository;
import com.rental.haedal.auth.dto.req.LoginRequest;
import com.rental.haedal.auth.dto.req.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final AuthUtil authUtil;

    long expireTimeMs = 1000 * 60 * 60 * 12;
    long refreshExpireTimeMs = 1000 * 60 * 60 * 24 * 14;

    // Login
    @Transactional
    public Map<String, String> login(LoginRequest request) {
        System.out.println("오류발생!!!!" + request.id() + " " + request.password());
        Optional<Member> member = memberRepository.findByUserIdAndPassword(request.id(), request.password());
        if (!member.isPresent()) {
            System.out.println("아이디랑 비밀번호 잘못됨");
            throw new RuntimeException("아이디 또는 비밀번호가 잘못되었습니다.");
        }

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", authUtil.createAccessToken(member.get().getUserId(), expireTimeMs));
        tokens.put("refreshToken", authUtil.createRefreshToken(member.get().getUserId(), refreshExpireTimeMs));

        return tokens;
    }

    // Sign Up
    @Transactional
    public void signup(SignUpRequest request) {
        Member member = Member.builder()
                .userId(request.id())
                .password(request.password())
                .userName(request.userName())
                .phoneNumber(request.phoneNumber())
                .build();

        memberRepository.save(member);
    }

    // Logout
//    public LogoutResponse logout(LogoutRequest request) {
//
//    }

    //JWT 전체 보기 먼저 해야할듯해서 일단 STOP
}
