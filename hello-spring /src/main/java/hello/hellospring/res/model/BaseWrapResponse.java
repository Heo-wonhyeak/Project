package hello.hellospring.res.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseWrapResponse implements Serializable {

    private static final long serialVersionUID = -8973531112384983471L;

    private String resultMessage;
    private int resultCode;

    private Object data;
}
