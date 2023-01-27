package hello.hellospring.req.model.board;

import lombok.Data;

@Data
public class HugoBoardListReqModel {

    private Integer startPage;

    private Integer listCount;
}
