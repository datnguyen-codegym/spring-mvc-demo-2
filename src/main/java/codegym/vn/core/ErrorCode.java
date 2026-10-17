package codegym.vn.core;

public enum ErrorCode {
    UNKNOW_ERROR(500, "Lien he quan tri vien de duoc ho tro"),
    RETRY(400, "Thao tac loi, vui long thuc hien lai")

    ;
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
