package com.example.bucketplace.domain.member.dto;

import com.example.bucketplace.domain.member.entity.Member;
import com.example.bucketplace.domain.member.entity.type.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

public class MemberRequestDto {

    @Getter
    public static class SignupMemberRequestDto {

        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "이메일 형식에 맞춰주세요.")
        private String email;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(
                regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{8,}$",
                message = "영문, 숫자를 포함한 8자 이상의 비밀번호를 입력하세요."
        )
        private String password;

        @NotBlank(message = "비밀번호 확인을 입력해주세요.")
        private String passwordCheck;

        @NotBlank(message = "닉네임을 입력해주세요.")
        private String nickname;

        public Member toEntity(String encodedPassword) {
            return Member.builder()
                    .email(this.email)
                    .password(encodedPassword)
                    .nickname(this.nickname)
                    .role(RoleType.ROLE_USER)
                    .build();
        }
    }
}
