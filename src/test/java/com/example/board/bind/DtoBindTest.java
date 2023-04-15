package com.example.board.bind;

import com.example.board.dto.member.MemberSaveRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.validation.BindException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class DtoBindTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("회원가입 DTO 바인딩 테스트")
    void memberSaveBindTest() throws Exception {
        MemberSaveRequestDTO memberSaveRequestDTO = new MemberSaveRequestDTO();
        memberSaveRequestDTO.setEmail("testemail@test.com");
        memberSaveRequestDTO.setId("test");
        memberSaveRequestDTO.setNickname("test");
        memberSaveRequestDTO.setPassword("123Qq!2233q");

        MvcResult result = mockMvc.perform(post("/permitAll/member/save")
                        .contentType(MediaType.APPLICATION_JSON)   // json type 으로 요청
                        .content(objectMapper.writeValueAsString(memberSaveRequestDTO))   // memberSaveRequestDTO를 json 문자열로 변환하여 objectMapper를 통해 body에 추가
                ).andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        //예외 비정상 적인 값 ex ) -> password : 대/소문자 + 특수문자 사용해서 안할경우  or email 형식이 아닐경우 발생 확인!! ( 정상 )
//        Assertions.assertThrows(BindException.class, () -> {   // Assertions.assertThrows 예외를 강제로 던진다
//            log.info("result.getResolvedException() = {}" , result.getResolvedException().getClass().getSimpleName());
//            throw result.getResolvedException();
//        });
    }
}