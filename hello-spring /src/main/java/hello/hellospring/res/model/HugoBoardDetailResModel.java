package hello.hellospring.res.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import hello.hellospring.mybatis.model.HugoBoardReplyModel;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class HugoBoardDetailResModel {

    private Long boardIdx;

    private String title;

    private String content;

    private String eventPeriod;

    private String id;

    private String oFile;

    private Long likeCount;

    private Long visitCount;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Date createDate;

    private String writeHeader;

    private String boarder;

    private List<HugoBoardReplyModel> replyInfo;

    private String fileCode;
}
