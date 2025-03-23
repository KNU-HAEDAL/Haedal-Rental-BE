package com.rental.haedal.auth.service;

import com.rental.haedal.auth.domain.Member;
import com.rental.haedal.auth.domain.MemberRepository;
import com.rental.haedal.auth.dto.req.LoginRequest;
import com.rental.haedal.auth.dto.req.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final AuthUtil authUtil;

    // Login
    @Transactional
    public void login(LoginRequest request) {


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
