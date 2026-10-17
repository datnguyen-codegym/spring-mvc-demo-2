package codegym.vn.core;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {

    private ErrorCode errorCode = ErrorCode.UNKNOW_ERROR;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMsg());
    }
}
