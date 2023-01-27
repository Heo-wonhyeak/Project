package hello.hellospring.framework.exception;

import hello.hellospring.enums.ErrorCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <pre>
 * 사용법
 * 필요한 예외 처리하는 부분에 throw new BaseException();
 * 필요한 함수 인자값을 입력한다.
 * ex) throw new BaseException(ErrorCodeEnum.CONNECTION_TIMEOUT.msg(), ErrorCodeEnum.CONNECTION_TIMEOUT)
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = -800841598125361452L;

    private ErrorCodeEnum resultCode;
    private String resultMessage;
    private Object errorData;

    public BaseException() {
        super();
        this.resultMessage = ErrorCodeEnum.SYSTEM_ERROR.msg();
        this.resultCode = ErrorCodeEnum.SYSTEM_ERROR;
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.resultMessage = message;
        this.resultCode = ErrorCodeEnum.SYSTEM_ERROR;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.resultMessage = message;
        this.resultCode = ErrorCodeEnum.SYSTEM_ERROR;
    }

    public BaseException(String message) {
        super(message);
        this.resultMessage = message;
        this.resultCode = ErrorCodeEnum.SYSTEM_ERROR;
    }

    public BaseException(Throwable cause) {
        super(cause);
        this.resultCode = ErrorCodeEnum.SYSTEM_ERROR;
    }

    public BaseException(String message, ErrorCodeEnum errorCode){
        super(message);

        this.resultCode = errorCode;
        this.resultMessage = message;

    }

    public BaseException(String message, ErrorCodeEnum errorCode, Object errorData){
        super(message);

        this.resultCode = errorCode;
        this.resultMessage = message;
        this.errorData = errorData;
    }
}
