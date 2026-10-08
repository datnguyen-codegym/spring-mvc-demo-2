package codegym.vn.utils;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UsernameValidationUtils extends ValidationUtils {

    private static final List<String> vietnameseFistName = List.of("Triệu", "Đinh", "Lý", "Trần");

    public static void mustBeVietnameseFirstname(Errors errors, String field, String errorCode) {
        Assert.notNull(errors, "Errors object must not be null");
        Object value = errors.getFieldValue(field);
        String defaultMessage = "unknow.error";

        if (null != value
                && StringUtils.hasLength(value.toString())
                && vietnameseFistName.stream()
                .anyMatch(e -> value.toString().startsWith(e))
        ) return;

        errors.rejectValue(field, errorCode, null, defaultMessage);
    }


}
