package hello.hellospring.req.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import hello.hellospring.enums.ErrorCodeEnum;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class HugoUserSaveReqModel {
    @NotEmpty(message = "아이디가 공백입니다.")
    private String id;

    private String pwd;

    private String name;

    private String nickName;

    private String email;

    private Date birthDay;

    private String gender;

    private String callNum;

    private String interest;
}
