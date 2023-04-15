package com.example.board.dto.member;

import com.example.board.util.regexp.Regexp;
import lombok.Data;
import org.apache.ibatis.type.Alias;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Data
@Alias("memberSaveRequestDTO")
public class MemberSaveRequestDTO {
    UUID uuid = new UUID();

    @Pattern(regexp = Regexp.ID_REG , message = "영어 , 숫자 조합으로 아이디를 입력해주세요.")
    private String id;
    @Pattern(regexp = Regexp.PASSWORD_REG , message = "숫자 , 영어, 특수문자 포함 8~16자리로 입력해주세요.")
    private String password;
    @Pattern(regexp = Regexp.EMAIL_REG , message = "이메일 형식으로 입력해주세요.")
    private String email;
    @Pattern(regexp = Regexp.NICKNAME_REG , message = "툭수문자 및 공백 사용 불가 2~10 자리로 입력해주세요.")
    private String nickname;

    public void setIdx(UUID uuid) {
        this.uuid = uuid;
    }
}
