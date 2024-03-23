package com.example.bucketplace.domain.member.service;

import com.example.bucketplace.domain.member.dto.MemberRequestDto.SignupMemberRequestDto;
import com.example.bucketplace.domain.member.dto.MemberResponseDto.CheckMemberResponseDto;
import com.example.bucketplace.domain.member.dto.MemberResponseDto.SignupMemberResponseDto;
import com.example.bucketplace.domain.member.entity.Member;
import com.example.bucketplace.domain.member.repository.MemberRepository;
import com.example.bucketplace.global.exception.BadRequestException;
import com.example.bucketplace.global.exception.ErrorCode;
import com.example.bucketplace.global.exception.RefreshTokenException;
import com.example.bucketplace.global.jwt.TokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public MemberService(MemberRepository memberRepository, RedisTemplate redisTemplate, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        this.memberRepository = memberRepository;
        this.redisTemplate = redisTemplate;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
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

    @Transactional
    public void reissue(String refreshToken, HttpServletResponse response) {
        if (refreshToken == null) {
            throw new RefreshTokenException(ErrorCode.NOT_FOUND_REFRESH_TOKEN.getMessage());
        }

        try {
            tokenProvider.isExpired(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new RefreshTokenException(ErrorCode.EXPIRED_REFRESH_TOKEN.getMessage());
        }

        String type = tokenProvider.getTokenType(refreshToken);
        if (!type.equals("refresh")) {
            throw new RefreshTokenException(ErrorCode.INVALID_REFRESH_TOKEN.getMessage());
        }

        String value = redisTemplate.opsForValue().get(refreshToken);
        if (value == null) {
            throw new RefreshTokenException(ErrorCode.INVALID_REFRESH_TOKEN.getMessage());
        }

        String email = tokenProvider.getTokenEmail(refreshToken);
        String role = tokenProvider.getTokenRole(refreshToken);
        redisTemplate.delete(refreshToken);

        String newAccessToken = tokenProvider.createAccessToken(email, role);
        String newRefreshToken = tokenProvider.createRefreshToken(email, role);

        response.addHeader(TokenProvider.AUTHORIZATION_HEADER, newAccessToken);
        tokenProvider.addRefreshTokenToCookie(newRefreshToken, response);
    }

    @Transactional
    public void logout(String refreshToken, HttpServletResponse response) {
        if (refreshToken == null) {
            throw new RefreshTokenException(ErrorCode.NOT_FOUND_REFRESH_TOKEN.getMessage());
        }

        try {
            tokenProvider.isExpired(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new RefreshTokenException(ErrorCode.EXPIRED_REFRESH_TOKEN.getMessage());
        }

        String type = tokenProvider.getTokenType(refreshToken);
        if (!type.equals("refresh")) {
            throw new RefreshTokenException(ErrorCode.INVALID_REFRESH_TOKEN.getMessage());
        }

        String value = redisTemplate.opsForValue().get(refreshToken);
        if (value == null) {
            throw new RefreshTokenException(ErrorCode.INVALID_REFRESH_TOKEN.getMessage());
        }

        redisTemplate.delete(refreshToken);

        Cookie cookie = new Cookie(TokenProvider.REFRESH_TOKEN_COOKIE, null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setAttribute("SameSite", "None");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
    }

    public CheckMemberResponseDto check(String type, String value) {
        return switch (type) {
            case "email" -> new CheckMemberResponseDto(memberRepository.existsByEmail(value));
            case "nickname" -> new CheckMemberResponseDto(memberRepository.existsByNickname(value));
            default -> throw new BadRequestException(ErrorCode.BAD_REQUEST_DUPLICATION_TYPE.getMessage());
        };
    }
}
