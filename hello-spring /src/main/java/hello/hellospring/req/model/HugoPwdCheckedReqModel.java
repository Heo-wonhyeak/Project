package hello.hellospring.req.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class HugoPwdCheckedReqModel {

    @NotEmpty(message = "id가 공백입니다.")
    private String id;

    private String pwd;
}
