package com.example.board.util.jwt;


import com.example.board.dto.member.GetMemberInfoByJwtDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class JwtProvider {

    @Value("${spring.jwt.secret}")
    private String SPRING_JWT_SECRET;

    //private JwtProvider jwtProvider;


    /**
     * 로그인한 사용자의 정보
     * @return
     */
    public GetMemberInfoByJwtDTO getMember(){
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String){
            return null;
        }
        return (GetMemberInfoByJwtDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 토큰 발급
     * @param memberIdx member table index
     * @param role member authentication
     * @return
     */
    public String generateToken(int memberIdx, String role){
        return Jwts.builder()
                .claim("memberIdx", memberIdx)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(getExpiration())
                .signWith(getKey())
                .compact();
    }

    /**
     * 만료일 지정
     * @return
     */
    private Date getExpiration(){
        return Date.from(LocalDateTime.now().with(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * jwt Token Sign
     * @return
     */
    private Key getKey(){
        return Keys.hmacShaKeyFor(SPRING_JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 토큰 만료 검증
     * @param token jwt token
     * @return exprire Check
     */
    public boolean validateToken(String token){
        try{
            final Date expiration = getClaims(token).getExpiration();
            return !expiration.before(new Date());
        }catch (ExpiredJwtException e){
            e.printStackTrace();
            return false;
        }
    }

    public Long getMemberIdx(String token) {
        Claims claims = getClaims(token);
        return claims.get("memberIdx", Long.class).longValue();
    }
//    public int getMemberIndex(String token){
//        Claims claims = getClaims(token);
//        return claims.get("memberIdx", Integer.class).intValue();
//    }

    public String getRole(String token){
        Claims claims = getClaims(token);
        return claims.get("role", String.class);
    }

    /**
     * Claims Information
     * @param token jwt token
     * @return Claums Information
     */
    private Claims getClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    }


}
