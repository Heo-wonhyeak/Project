package hello.hellospring.req.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
public class HugoUserModifyReqModel {

    private String id;

    private String pwd;

    private String name;

    @Getter
    private String nickName;

    private String email;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    private Date birthDay;

    private String gender;

    private String callNum;

    private String interest;
}
