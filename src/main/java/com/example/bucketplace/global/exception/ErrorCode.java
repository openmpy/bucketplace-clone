package com.example.bucketplace.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    INTERNAL_SERVER_ERROR("내부 서버 오류가 발생했습니다."),
    ALREADY_EXIST_MEMBER_EMAIL("이미 가입된 회원 이메일입니다."),
    ALREADY_EXIST_MEMBER_NICKNAME("이미 가입된 회원 닉네임입니다."),
    NOT_MATCH_PASSWORD_AND_CHECK_PASSWORD("비밀번호와 비밀번호 확인이 일치하지 않습니다."),
    NOT_FOUND_MEMBER_EMAIL("찾을 수 없는 회원 이메일입니다."),
    FAIL_LOGIN("로그인에 실패했습니다."),
    EXPIRED_ACCESS_TOKEN("Access Token 유효 시간이 만료되었습니다."),
    INVALID_ACCESS_TOKEN("유효하지 않은 Access Token입니다."),
    ;

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }
}
