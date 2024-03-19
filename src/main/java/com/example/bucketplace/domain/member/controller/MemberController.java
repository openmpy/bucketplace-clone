package com.example.bucketplace.domain.member.controller;

import com.example.bucketplace.domain.member.dto.MemberResponseDto.SignupMemberResponseDto;
import com.example.bucketplace.domain.member.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.example.bucketplace.domain.member.dto.MemberRequestDto.SignupMemberRequestDto;

@RequestMapping("/api/v1/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public SignupMemberResponseDto signup(@RequestBody @Valid SignupMemberRequestDto requestDto) {
        return memberService.signup(requestDto);
    }
}
