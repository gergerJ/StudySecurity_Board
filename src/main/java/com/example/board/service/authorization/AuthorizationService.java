package com.example.board.service.authorization;

import com.example.board.dto.member.AuthorizationMemberResponseDTO;

public interface AuthorizationService {

    public AuthorizationMemberResponseDTO memberAuthorizationFindById(Long idx);
}
