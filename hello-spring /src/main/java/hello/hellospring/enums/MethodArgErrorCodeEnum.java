package hello.hellospring.enums;

import lombok.Getter;

public enum MethodArgErrorCodeEnum {
    NOT_EMPTY("2000", "NOT EMPTY"),
    NOT_NULL("2001","NOT_NULL"),
    NOT_BLANK("2002", "NOT_BLANK"),
    MIN_VALUE("2003", "최소값보다 커야 합니다.")
    ;

    @Getter
    private String code;

    @Getter
    private String description;

    MethodArgErrorCodeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

}
