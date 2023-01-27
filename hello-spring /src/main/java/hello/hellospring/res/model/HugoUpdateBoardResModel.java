package hello.hellospring.res.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class HugoUpdateBoardResModel {

    private Long boardIdx;

    private String title;

    private String id;


}
