package hello.hellospring.enums;

public enum ErrorCodeEnum {

    CONNECTION_TIMEOUT(522, "Connection Time out Exception"),
    FILE_NOT_FOUND(523, "File Not Found Exception"),
    IOE_ERROR(524, "IOE Exception"),

    CUSTOM_ERROR_ID_OVERLAP(552,"이미 존재하는 아이디 입니다."),
    CUSTOM_ERROR_NICKNAME_OVERLAP(553, "이미 존재하는 닉네임 입니다."),

    CUSTOM_ERROR_NOT_TITLE(600, "제목이 입력되지 않았습니다"),
    CUSTOM_ERROR_NULL_BOARD_IDX(601, "게시판 번호를 입력해주세요"),
    CUSTOM_ERROR_NULL_BOARD_DETAIL(602, "해당 번호의 게시글은 존재하지 않습니다"),
    CUSTOM_ERROR_NULL_BOARD_REPLY(603, "해당 번호의 댓글은 존재하지 않습니다"),
    CUSTOM_ERROR_NULL_BOARD(604, "게시글이 존재하지 않습니다"),

    CUSTOM_EMPTY_MEMBER_LIST(700, "회원목록이 없습니다."),
    CUSTOM_ERROR_LOGIN(701, "로그인시 에러 발생."),
    CUSTOM_ERROR_NOT_FOUND_USER(702, "아이디 혹은 패스워드를 확인해주세요"),
    CUSTOM_ERROR_USER_INFO_NULL(703, "회원정보가 없습니다."),
    CUSTOM_ERROR_NOT_LOGIN(704, "로그인되어있지 않습니다"),

    CUSTOM_ERROR_NOT_CORRECT_USER(710, "작성자 본인만 삭제가 가능합니다"),

    CUSTOM_ERROR_NOT_NULLABLE(800, "값이 없으면 안됩니다"),

    JSON_PARSE_EXCEPTION_ERROR(901, "JSON PARSE EXCEPTION ERROR"),
    STRING_ENCODING_EXCEPTION_ERROR(902, "JSON PARSE EXCEPTION ERROR"),

    SYSTEM_ERROR(999, "오류가 발생하였습니다. 잠시후 재시도 부탁드립니다.") // System Code
    ;

    int code;
    String msg;

    ErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int code() {
        return this.code;
    }

    public String msg() {
        return this.msg;
    }

    public static String getEventErrorMessage(int code) {
        for (ErrorCodeEnum errCode : ErrorCodeEnum.values()) {
            if (code == errCode.code) {
                return errCode.msg();
            }
        }
        return null;
    }
}
