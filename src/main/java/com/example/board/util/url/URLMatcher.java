package com.example.board.util.url;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

//@Component
@Slf4j
public class URLMatcher implements RequestMatcher {

    private final OrRequestMatcher requestMatcher;

    public static URLMatcher createURLMatcher (List<String> pathList){
        log.info("createURLMatcher 생성 ");
        return new URLMatcher(pathList);
    }
    public URLMatcher(List<String> pathList){
        log.info("URLMatcher 생성자에서 URLMatcher 생성 ");
        //if(!pathList.isEmpty()){
            List<RequestMatcher> requestMatcherList = pathList.stream()
                   .map(AntPathRequestMatcher::new)
                    //.map(result -> new AntPathRequestMatcher(result) )  // 람다식 표현방법
                    .collect(Collectors.toList());

            requestMatcher = new OrRequestMatcher(requestMatcherList);  //
        //}
    }

    @Override
    public boolean matches(HttpServletRequest request){
        log.info("matches 의 request :: " + request);
        log.info("requestMatcher.matches(request) :: " + requestMatcher.matches(request));
        return requestMatcher.matches(request);
    }
}
