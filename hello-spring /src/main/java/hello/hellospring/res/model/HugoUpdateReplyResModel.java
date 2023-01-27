package hello.hellospring.res.model;

import lombok.Data;

@Data
public class HugoUpdateReplyResModel {

    private Long boardReplyIdx;

    private String nickName;

    private Long boardIdx;
}
