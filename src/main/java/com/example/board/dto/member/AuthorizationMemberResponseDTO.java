package com.example.board.dto.member;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("authorizationMemberResponseDTO")
public class AuthorizationMemberResponseDTO {

    private String id;
    private Long idx;
    private String role;
    private boolean enabled;
}
