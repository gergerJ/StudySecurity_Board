package com.example.board.config;

import com.example.board.mapper.MemberMapper;
import com.example.board.service.authorization.AuthorizationService;
import com.example.board.service.authorization.AuthorizationServiceImpl;
import com.example.board.util.filter.JwtExceptionFilter;
import com.example.board.util.filter.JwtFilter;
import com.example.board.util.jwt.CustomAccessDeniedHandler;
import com.example.board.util.jwt.JwtProvider;
import com.example.board.util.url.URLMatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Configuration
@EnableWebSecurity  // @Configuration 이 필요한데, 내부에 들어있기 때문에 생략 가능 ,하지만 달아주는걸 추천
public class SecurityConfig {

    private final String[] CORS_ALLOW_METHOD = {"GET", "POST", "PATCH", "DELETE", "OPTIONS"};


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        log.info("SecurityConfig 2번 securityFilterChain");
        http.cors();  // HTTP 요청을 할 때 설정을 위한 것
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // REST API 로 기본적으로 Session을 가지고 있지 않음.
                .and()  // securityConfigurer 사용이 완료되면,  SecurityBuilder 를 반환한다. Chaining에 유용함
                .authorizeRequests()  // RequestMatcher을 사용하여 HttpServletRequest 를 기반으로 액세스를 제한 할 수 있음
                .antMatchers("/login" , "/permitAll/**").permitAll()  // login url 에 대해서 모든 사람이 요청할 수 있다는 것을 명시(권한을 설정하는 부분) // 단일 맵핑하는 구조 (URLMatcher는 리스트 구조
                                                                                // authenticated()는 승인받은 (권한있는) 사용자만 접근가능
                .anyRequest()                           // 요청에 대한 권한 지정 -> 나머지 요청에 대해서는 권한이 필요함을 명시
                .authenticated()                    // 인증된 사용자만 접근할 수 있음을 설정
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .csrf().disable();
                //csrf 토큰은 포스트 요청시에 크로스사이트공격을 방지하기 위한 토큰으로써 jwt 토큰을 활용한 rest 요청시에는 필요하지 않습니다
                //jwt 토큰 자체가 식별자로 사용되기 때문입니다.
        http.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);  // bean 등록
        http.addFilterBefore(jwtExceptionFilter(), JwtFilter.class);   // bean 등록  // JwtFilter.class 예외 발생 시 jwtExceptionFilter()에서 예외 처리함!

        log.info("SecurityConfig 2-1번 securityFilterChain");
        return http.build();
    }

    //클라이언트가 서버로 요청할 때 security 를 거치지 않는 url 을 지정하는 부분 이라고 생각하면 됩니다.
    //git.ignore 와 같은 이치
    @Bean
    public WebSecurityCustomizer customWebSecurityCustomizer() {
        log.info("SecurityConfig 4번 customWebSecurityCustomizer");
        return (web) -> web.ignoring().antMatchers("/csrf" ,  "/" ,"/swagger-resources/**" ,"/swagger-ui.html/**" , "/v2/api-docs/**" , "/webjars/**");
    }


    //패스워드를 암호화하는 기법을 설정
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        log.info("SecurityConfig 1번 bCryptPasswordEncoder");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        log.info("SecurityConfig 3번 corsConfigurationSource");
        CorsConfiguration configuration = new CorsConfiguration();
        //
        configuration.setAllowedMethods(Arrays.asList(CORS_ALLOW_METHOD));
        configuration.addAllowedHeader("*");
        configuration.addAllowedOrigin("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public JwtFilter jwtFilter(){
        log.info("jwtFilter 필터가 1번 ");
        return new JwtFilter();
    }

    @Bean
    public URLMatcher urlMatcher() {
        log.info("urlMatcher  맷쳐가 2번 ");
        List<String> pathList = createAuthPathList("/test" , "/permitAll/test");
        return URLMatcher.createURLMatcher(pathList);
    }

    private List<String> createAuthPathList(String ...pathParam){
        log.info("당연히 3번이지 ");
        List<String> pathList = new ArrayList<>();
        Collections.addAll(pathList, pathParam);
        return pathList;
    }

    @Bean
    public JwtProvider jwtProvider(){
        return new JwtProvider();
    }

    @Bean
    public JwtExceptionFilter jwtExceptionFilter(){
        return new JwtExceptionFilter();
    }

//    @Bean
//    public AuthorizationService authorizationService(MemberMapper memberMapper){ return new AuthorizationServiceImpl(memberMapper);}

}