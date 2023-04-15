package com.example.board.service.member;

import com.example.board.dto.member.MemberSaveRequestDTO;
import com.example.board.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{

    private final MemberMapper memberMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public boolean memberSave(MemberSaveRequestDTO memberSaveRequestDTO) {
        int idByExistCount = memberMapper.memberIdDuplicationCheck(memberSaveRequestDTO.getId());
        int emailByExistCount = memberMapper.memberEmailDuplicationCheck(memberSaveRequestDTO.getEmail());

        if(idByExistCount > 0 || emailByExistCount > 0)
            return false;
        else{
            UUID uuid = UUID.randomUUID();
            memberSaveRequestDTO.setIdx(uuid);
            memberSaveRequestDTO.setPassword(bCryptPasswordEncoder.encode(memberSaveRequestDTO.getPassword()));
            int result = memberMapper.memberSave(memberSaveRequestDTO);

            return result > 0;   //  ??
        }
    }
}
