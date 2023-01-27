package hello.hellospring.mybatis.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class HugoBoardModel {

    private Long rowNum;

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

    //파일 코드 받기
    private String fileCode;
}
