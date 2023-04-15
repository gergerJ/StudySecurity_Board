package com.example.board.util.filter;

import com.example.board.util.jwt.JwtExceptionEnum;
import com.example.board.util.jwt.ValidationFailResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e){
            log.info("ExceptionFilter 123 = {}" , e.getMessage());
            JwtExceptionEnum byErrorReason = JwtExceptionEnum.findByErrorReason(e.getClass().getSimpleName());
            ValidationFailResponse validationFailResponse = new ValidationFailResponse();
            validationFailResponse.validationResponse(response, byErrorReason);
        }
    }
}