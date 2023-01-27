package hello.hellospring.req.model.board;

import lombok.Data;

@Data
public class HugoWriteBoardReqModel {

    private String title;

    private String content;

    private String eventPeriod;

    private String id;

    private String oFile;

    private String writeHeader;

    private String boarder;
}

