package com.example.board.util.filter;

import com.example.board.dto.member.AuthorizationMemberResponseDTO;
import com.example.board.mapper.MemberMapper;
import com.example.board.service.authorization.AuthorizationService;
import com.example.board.util.jwt.JwtProvider;
import com.example.board.util.url.URLMatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Value("${spring.jwt.header}")
    private String SPRING_JWT_HEADER;
    private URLMatcher urlMatcher;

    private JwtProvider jwtProvider;

    private AuthorizationService authorizationService;

    @Autowired
    public void setJwtProvider(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Autowired
    public void setUrlMatcher(URLMatcher urlMatcher){  // 생성자 주입 이후에 Autowired 진행
        this.urlMatcher = urlMatcher;
    }

    @Autowired
    public void setAuthorizationService(AuthorizationService authorizationService){ this.authorizationService = authorizationService; }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{

        log.info("doFilterInternal 접속!!!!");
        log.info("urlMatcher :: " + urlMatcher);
        log.info("request :: " + request);
        log.info("response :: " + response);
        log.info("filterChain :: " + filterChain);

//        if(urlMatcher.matches(request)){
//            log.info("filter Test Request AUTH!!!!!!!!!!!!!!!");
//            filterChain.doFilter(request, response);
//            //return;
//            //위에 return 이 없을 경우 이 filterChain 이 작동이 끝나서, Controller 가 작동되며, 이때 Controller 의 리턴값으로 response 를 채우기 때문에
//            // 아래에 중복으로 response 를 사용할 수 없다!!
//        }
        log.info("@@@@@@@@@@@@@@@@@@@@@@@1 11111111111111111111111");
        // 크롬 OPTIONS 요청 처리
        if ("OPTIONS".equals(request.getMethod())){
            response.setStatus(HttpServletResponse.SC_OK);   // 요청 ok 코드 200 보여주기
            return;
        }
        log.info("filter TEST Request");
        //if(menuAuthMatcher.matches(request))

        log.info("@@@@@@@@@@@@@@@@@@@@@@@22222222222222222222222222");

        final String token = request.getHeader(SPRING_JWT_HEADER);
        if (urlMatcher.matches(request)){
            if (token == null) {
                log.info("@@@@@@@@@@@@@@@@@@@@@@@33333333333333333333333333");
                //토큰이 없으면 에러처리
                throw new IllegalArgumentException("test Exception 발생");
                //throw 발생 시 if문이 종료되며, 호출한 곳으로 돌아간다 !  -> JwtFilter 의 Exception 처리는 JwtExceptionFilter에서 진행되며,
                // 이유는 SecurityConfig Class 에서 securityFilterChain 52번줄에 설정했기 때문이다.
            }
            log.info("@@@@@@@@@@@@@@@@@@@@@@@4444444444444444444444444444");

            if(!jwtProvider.validateToken(token)){
                //토큰이 만료되었으면 에러처리
            }

            long memberIdx = jwtProvider.getMemberIdx(token);
            AuthorizationMemberResponseDTO member = authorizationService.memberAuthorizationFindById(memberIdx);

            log.info("urlMatcher if문 안의   filter TEST Request AUTH!!!!!!!!!!");
            //filterChain.doFilter(request, response);
        }

        filterChain.doFilter(request, response);


    }


//    private String test(){
//
////        while(true){
////            return "1";
////        }
//    }




}
