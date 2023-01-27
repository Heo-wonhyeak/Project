package hello.hellospring.mybatis.model;

import lombok.Data;

@Data
public class HugoBoardReplyDeclarationModel {

    private Long declarationIdx;

    private String reason;

    private String id;

    private Long boardReplyIdx;

    private String content;
}
