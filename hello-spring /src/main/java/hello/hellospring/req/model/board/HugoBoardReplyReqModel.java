package hello.hellospring.req.model.board;

import lombok.Data;

@Data
public class HugoBoardReplyReqModel {

    private String nickName;

    private String content;

    private Long boardIdx;

}
