package com.example.board.util.jwt;

import com.example.board.util.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  security exception handler 리스폰 객체
 */
public class ValidationFailResponse {

    public void validationResponse(HttpServletResponse response, JwtExceptionEnum jwtEnum) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(
                objectMapper.writeValueAsString(
                        Response.builder( jwtEnum.getMessage() , jwtEnum.getCode()).build()  // 추가
                )
        );
    }
}