package hello.hellospring.req.model.board;

import lombok.Data;

@Data
public class HugoUpdateBoardReqModel {

    private Long boardIdx;

    private String title;

    private String content;

    private String eventPeriod;

    private String oFile;

    private String writeHeader;

    private String boarder;
}
