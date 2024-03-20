package com.example.bucketplace.domain.member.controller;

import com.example.bucketplace.domain.member.controller.docs.MemberControllerDocs;
import com.example.bucketplace.domain.member.dto.MemberResponseDto.CheckMemberResponseDto;
import com.example.bucketplace.domain.member.dto.MemberResponseDto.SignupMemberResponseDto;
import com.example.bucketplace.domain.member.service.MemberService;
import com.example.bucketplace.global.dto.ResponseDto;
import com.example.bucketplace.global.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.example.bucketplace.domain.member.dto.MemberRequestDto.SignupMemberRequestDto;

@RequestMapping("/api/v1/members")
@RestController
public class MemberController implements MemberControllerDocs {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public ResponseDto<SignupMemberResponseDto> signup(@RequestBody @Valid SignupMemberRequestDto requestDto) {
        SignupMemberResponseDto responseDto = memberService.signup(requestDto);
        return ResponseDto.success("회원가입 기능", responseDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/reissue")
    public void reissue(@CookieValue(TokenProvider.REFRESH_TOKEN_COOKIE) String refreshToken, HttpServletResponse response) {
        memberService.reissue(refreshToken, response);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/logout")
    public void logout(@CookieValue(TokenProvider.REFRESH_TOKEN_COOKIE) String refreshToken, HttpServletResponse response) {
        memberService.logout(refreshToken, response);
    }

    @GetMapping("/check")
    public ResponseDto<CheckMemberResponseDto> check(@RequestParam("type") String type, @RequestParam("value") String value) {
        CheckMemberResponseDto responseDto = memberService.check(type, value);
        return ResponseDto.success("중복 검사", responseDto);
    }
}
