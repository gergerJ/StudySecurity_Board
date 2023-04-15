package com.example.board.util.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 인가 실패 시 핸들러 구현체
 */
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    //private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        ValidationFailResponse validationFailResponse = new ValidationFailResponse();
        validationFailResponse.validationResponse(response, JwtExceptionEnum.findByErrorReason(accessDeniedException.getClass().getSimpleName()));
    }
}
