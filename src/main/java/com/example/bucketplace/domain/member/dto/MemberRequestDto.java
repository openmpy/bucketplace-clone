package com.example.bucketplace.domain.member.dto;

import com.example.bucketplace.domain.member.entity.Member;
import com.example.bucketplace.domain.member.entity.type.RoleType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

public class MemberRequestDto {

    @Getter
    public static class SignupMemberRequestDto {

        @Schema(description = "이메일", example = "test@test.com")
        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "이메일 형식에 맞춰주세요.")
        private String email;

        @Schema(description = "비밀번호", example = "test123!")
        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(
                regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{8,}$",
                message = "영문, 숫자를 포함한 8자 이상의 비밀번호를 입력하세요."
        )
        private String password;

        @Schema(description = "비밀번호 확인", example = "test123!")
        @NotBlank(message = "비밀번호 확인을 입력해주세요.")
        private String passwordCheck;

        @Schema(description = "닉네임", example = "홍길동")
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

    @Getter
    public static class SigninMemberRequestDto {

        @Schema(description = "이메일", example = "test@test.com")
        @NotBlank(message = "이메일을 입력해주세요.")
        private String username;

        @Schema(description = "비밀번호 확인", example = "test123!")
        @NotBlank(message = "비밀번호를 입력해주세요.")
        private String password;
    }
}
