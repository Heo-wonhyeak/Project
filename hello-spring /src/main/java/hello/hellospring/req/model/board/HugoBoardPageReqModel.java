package hello.hellospring.req.model.board;

import lombok.Data;

@Data
public class HugoBoardPageReqModel {

    private Integer page;

    private Integer countPage;

    private Integer listCount;
}
