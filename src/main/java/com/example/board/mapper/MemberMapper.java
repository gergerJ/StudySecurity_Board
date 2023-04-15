package com.example.board.mapper;

import com.example.board.dto.member.AuthorizationMemberResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberMapper {
    Optional<AuthorizationMemberResponseDTO> memberAuthorizationFindById(Long idx);
    int memberIdDuplicationCheck(String id);
    int memberEmailDuplicationCheck(String email);
}
