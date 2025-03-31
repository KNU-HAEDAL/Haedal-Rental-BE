package com.rental.haedal.controller;

import com.rental.haedal.dto.auth.req.IdCheckRequest;
import com.rental.haedal.dto.auth.req.LoginRequest;
import com.rental.haedal.dto.auth.req.PhoneNumberCheckRequest;
import com.rental.haedal.dto.auth.req.SignUpRequest;
import com.rental.haedal.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Auth API")
public class AuthController {
    // 의존성 주입을 위해 띄움.
    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "사용자가 로그인을 함.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    public ResponseEntity<?> userLogin(@RequestBody @Parameter LoginRequest request) {
        try {
            // 로그인 요청 처리 및 토큰 생성
            Map<String, String> tokens = authService.login(request);

            // 토큰을 헤더에 추가
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + tokens.get("accessToken"));
            headers.add("Refresh-Token", tokens.get("refreshToken"));

            // 토큰을 헤더에 담아 성공 응답 반환
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .headers(headers)
                    .build();

        } catch (RuntimeException e) {
            // 인증 실패 시 401 Unauthorized 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/duplicate/id")
    @Operation(summary = "아이디 중복확인", description = "회원가입시 아이디가 중복되는지 확인. 중복 시 False 값을 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    public ResponseEntity<Boolean> registerIdCheck(@RequestBody @Parameter IdCheckRequest request) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/duplicate/phoneNumber")
    @Operation(summary = "전화번호 중복확인", description = "회원가입시 전화번호가 중복되는지 확인. 중복 시 False 값을 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    public ResponseEntity<Boolean> registerPhoneNumberCheck(@RequestBody @Parameter PhoneNumberCheckRequest request) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "사용자가 회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    public ResponseEntity<?> signup(@RequestBody @Parameter SignUpRequest request) {
        authService.signup(request);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/refresh")
    @Operation(summary = "토큰 재발급", description = "사용자 토큰을 재발급")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    public ResponseEntity<?> refreshToken() {
        return ResponseEntity.ok("토큰 재발급 성공");
    }

    @GetMapping("/logout")
    @Operation(summary = "로그아웃", description = "사용자가 로그아웃. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok("로그아웃 성공");
    }

}
