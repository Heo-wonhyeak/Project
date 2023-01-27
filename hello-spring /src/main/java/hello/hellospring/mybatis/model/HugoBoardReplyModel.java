package hello.hellospring.mybatis.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class HugoBoardReplyModel {

    private Long boardReplyIdx;

    private String nickName;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Date writeDate;

    private String content;

    private Long boardIdx;

}
