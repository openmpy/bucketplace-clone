package com.example.bucketplace.global.security;

import com.example.bucketplace.domain.member.dto.MemberRequestDto.SigninMemberRequestDto;
import com.example.bucketplace.domain.member.dto.MemberResponseDto.SigninMemberResponseDto;
import com.example.bucketplace.global.exception.ErrorCode;
import com.example.bucketplace.global.jwt.TokenProvider;
import com.example.bucketplace.global.util.CustomResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final TokenProvider tokenProvider;

    public JwtAuthenticationFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
        setFilterProcessesUrl("/api/v1/members/signin");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            SigninMemberRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), SigninMemberRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String email = ((UserDetailsImpl) authResult.getPrincipal()).getMember().getEmail();
        String role = ((UserDetailsImpl) authResult.getPrincipal()).getMember().getRole().name();

        String accessToken = tokenProvider.createAccessToken(email, role);
        String refreshToken = tokenProvider.createRefreshToken(email, role);

        response.addHeader(TokenProvider.AUTHORIZATION_HEADER, accessToken);
        tokenProvider.addRefreshTokenToCookie(refreshToken, response);

        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        SigninMemberResponseDto responseDto = new SigninMemberResponseDto(userDetails.getMember());
        CustomResponseUtil.success(response, responseDto);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        CustomResponseUtil.fail(response, ErrorCode.FAIL_LOGIN.getMessage(), HttpStatus.FORBIDDEN);
    }
}
