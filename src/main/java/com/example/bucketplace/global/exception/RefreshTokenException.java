package com.example.bucketplace.global.exception;

import lombok.Getter;

@Getter
public class RefreshTokenException extends RuntimeException {

    public RefreshTokenException(String message) {
        super(message);
    }
}
