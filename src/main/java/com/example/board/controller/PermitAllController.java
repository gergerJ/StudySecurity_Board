package com.example.board.controller;

import com.example.board.dto.member.MemberSaveRequestDTO;
import com.example.board.mapper.TestMapper;
import com.example.board.util.response.Response;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
//@RequiredArgsConstructor
@RequestMapping("/permitAll")
public class PermitAllController {


    //private final ;

    //@RequiredArgsConstructor 의 기능
//    @Autowired
//    public PermitAllController(TestMapper testMapper) {
//        this.testMapper = testMapper;
//    }

    @GetMapping("/test")
    @ApiOperation(value = "Swagger TEST Method Name")
    public String permitAllTest(@RequestParam String parameter){
        log.info("Controller    13번 ~~@@@@@@@@@@@@@@@@@@131313131313131313");
        return "test Success";
    }

    @PostMapping("/member/save")
    public Response<String> memberSave(@RequestBody @Valid MemberSaveRequestDTO saveReqeustDTO , BindingResult bindingResult) throws BindException{
        if(bindingResult.hasErrors()){  //BindingResult를 통해 에러 발생 시 true를 반환하여, if문 실행
            throw new BindException(bindingResult);
        }
        return null;
    }
}
