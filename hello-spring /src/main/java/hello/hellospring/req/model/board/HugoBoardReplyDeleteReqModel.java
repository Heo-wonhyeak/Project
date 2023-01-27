package hello.hellospring.req.model.board;

import lombok.Data;

@Data
public class HugoBoardReplyDeleteReqModel {

    private String nickName;

    private Long boardReplyIdx;
}
