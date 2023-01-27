package hello.hellospring.req.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class HugoUserDeleteReqModel {

    @NotEmpty(message = "id가 공백입니다.")
    private String id;

    @NotEmpty(message = "pwd 가 공백입니다.")
    private String pwd;
}
