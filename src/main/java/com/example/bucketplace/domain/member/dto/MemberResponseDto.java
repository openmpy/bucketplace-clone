package com.example.bucketplace.domain.member.dto;

import com.example.bucketplace.domain.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

public class MemberResponseDto {

    @Getter
    public static class SignupMemberResponseDto {

        private final String email;
        private final String nickname;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private final LocalDateTime createdAt;

        public SignupMemberResponseDto(Member member) {
            this.email = member.getEmail();
            this.nickname = member.getNickname();
            this.createdAt = member.getCreatedAt();
        }
    }

    @Getter
    public static class SigninMemberResponseDto {

        private final String email;

        public SigninMemberResponseDto(Member member) {
            this.email = member.getEmail();
        }
    }

    @Getter
    public static class CheckMemberResponseDto {

        private final Boolean isExist;

        public CheckMemberResponseDto(Boolean isExist) {
            this.isExist = isExist;
        }
    }
}
