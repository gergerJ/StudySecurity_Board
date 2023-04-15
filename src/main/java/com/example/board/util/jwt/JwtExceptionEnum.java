package com.example.board.util.jwt;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum JwtExceptionEnum {
    INVALID_TOKEN(300, "재 로그인 해주세요." , List.of("IllegalArgumentException")),
    EXPIRE_TOKEN(301 , "만료된 토큰입니다. 재 로그인 해주세요." , List.of("IllegalStateException")),
    DEFAuLT( 500, "알 수 없는 서버 오류", List.of());

    private final int code;
    private final String message;
    private final List<String> exceptions;

    JwtExceptionEnum(int code, String message, List<String> exceptions) {
        this.code = code;
        this.message = message;
        this.exceptions = exceptions;
    }

    public static JwtExceptionEnum findByErrorReason(String exceptionName){
        return Arrays.stream(JwtExceptionEnum.values())
                .filter(exceptionEnum -> hasMessage(exceptionEnum, exceptionName))
                .findAny()
                .orElse(JwtExceptionEnum.DEFAuLT);
    }

    private static boolean hasMessage(JwtExceptionEnum exceptionEnum , String message){
        return exceptionEnum.exceptions.stream()
                .anyMatch(message::startsWith);
    }
}
