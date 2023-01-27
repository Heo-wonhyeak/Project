package hello.hellospring.framework.exception;

import hello.hellospring.enums.ErrorCodeEnum;

public class CommonException extends BaseException {
    public CommonException() {
        super();
    }

    public CommonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonException(String message) {
        super(message);
    }

    public CommonException(Throwable cause) {
        super(cause);
    }

    public CommonException(String message, ErrorCodeEnum errorCode){
        super(message);
    }

    public CommonException(String message, ErrorCodeEnum errorCode, Object errorData){
        super(message);
    }
}
