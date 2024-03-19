package com.example.bucketplace.domain.member.service;

import com.example.bucketplace.domain.member.dto.MemberRequestDto.SignupMemberRequestDto;
import com.example.bucketplace.domain.member.dto.MemberResponseDto.SignupMemberResponseDto;
import com.example.bucketplace.domain.member.entity.Member;
import com.example.bucketplace.domain.member.repository.MemberRepository;
import com.example.bucketplace.global.exception.BadRequestException;
import com.example.bucketplace.global.exception.ErrorCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public SignupMemberResponseDto signup(SignupMemberRequestDto requestDto) {
        if (memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new BadRequestException(ErrorCode.ALREADY_EXIST_MEMBER_EMAIL.getMessage());
        }
        if (memberRepository.existsByNickname(requestDto.getNickname())) {
            throw new BadRequestException(ErrorCode.ALREADY_EXIST_MEMBER_NICKNAME.getMessage());
        }
        if (!requestDto.getPassword().equals(requestDto.getPasswordCheck())) {
            throw new BadRequestException(ErrorCode.NOT_MATCH_PASSWORD_AND_CHECK_PASSWORD.getMessage());
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        Member member = memberRepository.save(requestDto.toEntity(encodedPassword));
        return new SignupMemberResponseDto(member);
    }
}
