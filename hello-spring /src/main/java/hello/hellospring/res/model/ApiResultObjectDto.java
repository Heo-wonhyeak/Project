package hello.hellospring.res.model;

import hello.hellospring.enums.ErrorCodeEnum;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiResultObjectDto implements Serializable {

    private static final long serialVersionUID = 3799147531985378033L;

    private int resultCode;

    private Object result;

    private String resultMessage;

    @Builder
    public ApiResultObjectDto(Object result, int resultCode) {
        this.resultCode = resultCode;
        this.result = result == null ? "" : result;
        this.resultMessage = ErrorCodeEnum.getEventErrorMessage(resultCode) == null ? "SUCCESS" : ErrorCodeEnum.getEventErrorMessage(resultCode);
    }
}
