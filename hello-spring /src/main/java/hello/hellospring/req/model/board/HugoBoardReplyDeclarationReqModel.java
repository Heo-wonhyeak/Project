package hello.hellospring.req.model.board;

import lombok.Data;

@Data
public class HugoBoardReplyDeclarationReqModel {

    private String reason;

    private String id;

    private Long boardReplyIdx;

    private String content;
}
