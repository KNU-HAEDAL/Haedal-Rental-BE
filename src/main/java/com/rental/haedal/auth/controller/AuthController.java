package com.rental.haedal.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Auth API")
public class AuthController {
    // 의존성 주입을 위해 띄움.

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "사용자가 로그인을 함.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "실패"),
    })
    public ResponseEntity<Void> userLogin(){
        return ResponseEntity.ok().build();
    }

    @PostMapping("/duplicate/id")
    @Operation(summary = "아이디 중복확인", description = "회원가입시 아이디가 중복되는지 확인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "실패"),
    })
    public ResponseEntity<Void> registerIdCheck(){
        return ResponseEntity.ok().build();
    }

    @PostMapping("/duplicate/phoneNumber")
    @Operation(summary = "전화번호 중복확인", description = "회원가입시 전화번호가 중복되는지 확인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "실패"),
    })
    public ResponseEntity<Void> registerPhoneNumberCheck(){
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "사용자가 회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "실패"),
    })
    public ResponseEntity<Void> signup(){
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    @Operation(summary = "토큰 재발급", description = "사용자 토큰을 재발급")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "실패"),
    })
    public ResponseEntity<Void> refreshToken(){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/logout")
    @Operation(summary = "로그아웃", description = "사용자가 로그아웃")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "실패"),
    })
    public ResponseEntity<Void> logout(){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/userId")
    @Operation(summary = "ID 확인", description = "사용자 ID 확인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "실패"),
    })
    public ResponseEntity<Void> userId(){
        return ResponseEntity.ok().build();
    }

}
