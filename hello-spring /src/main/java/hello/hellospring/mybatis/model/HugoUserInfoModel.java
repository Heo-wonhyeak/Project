package hello.hellospring.mybatis.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.lang.Nullable;

import java.util.Date;

@ToString
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HugoUserInfoModel {

    private Long idx;

    private String id;

    private String pwd;

    private String name;

    private String nickName;

    private String email;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    private Date birthDay;

    private String gender;

    private String callNum;

    @Nullable
    private String interest;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Date joinDate;

}
