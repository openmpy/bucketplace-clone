package com.example.bucketplace.domain.member.controller.docs;

import com.example.bucketplace.domain.member.dto.MemberRequestDto.SignupMemberRequestDto;
import com.example.bucketplace.domain.member.dto.MemberResponseDto;
import com.example.bucketplace.domain.member.dto.MemberResponseDto.SignupMemberResponseDto;
import com.example.bucketplace.global.dto.ResponseDto;
import com.example.bucketplace.global.jwt.TokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "members", description = "회원 관련 API")
public interface MemberControllerDocs {

    @Operation(summary = "회원가입 기능", description = "회원가입 할 수 있는 API")
    ResponseDto<SignupMemberResponseDto> signup(@RequestBody @Valid SignupMemberRequestDto requestDto);

    @Operation(summary = "토큰 재발행 기능", description = "토큰을 재발행 할 수 있는 API")
    void reissue(@CookieValue(TokenProvider.REFRESH_TOKEN_COOKIE) String refreshToken, HttpServletResponse response);

    @Operation(summary = "로그아웃 기능", description = "로그아웃 할 수 있는 API")
    void logout(@CookieValue(TokenProvider.REFRESH_TOKEN_COOKIE) String refreshToken, HttpServletResponse response);

    @Operation(summary = "이메일, 닉네임 중복 검사 기능", description = "이메일, 닉네임 중복 검사를 할 수 있는 API")
    ResponseDto<MemberResponseDto.CheckMemberResponseDto> check(
            @Parameter(name = "type", description = "중복 검사할 수 있는 유형(email, nickname)", example = "email", required = true)
            @RequestParam("type") String type,
            @Parameter(name = "value", description = "중복 검사할 값", example = "test@test.com", required = true)
            @RequestParam("value") String value
    );
}
