package com.example.board.service.authorization;

import com.example.board.dto.member.AuthorizationMemberResponseDTO;
import com.example.board.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final MemberMapper memberMapper;

    public AuthorizationMemberResponseDTO memberAuthorizationFindById (Long idx){
        return memberMapper.memberAuthorizationFindById(idx)
                .orElseThrow(() -> {throw new UsernameNotFoundException("존재하지 않는 회원입니다.");});
    }
}
