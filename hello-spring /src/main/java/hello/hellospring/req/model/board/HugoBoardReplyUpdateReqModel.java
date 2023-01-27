package hello.hellospring.req.model.board;

import lombok.Data;

@Data
public class HugoBoardReplyUpdateReqModel {

    private Long boardReplyIdx;

    private String content;
}
