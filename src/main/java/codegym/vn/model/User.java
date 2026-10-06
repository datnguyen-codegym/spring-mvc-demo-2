package codegym.vn.model;


import codegym.vn.utils.UsernameValidationUtils;
import lombok.Data;
import lombok.Getter;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Data
public class User implements Validator {
    String username;
    int age;
    Gender gender;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User phoneNumber = (User) target;
        UsernameValidationUtils.mustBeVietnameseFirstname(errors, "usename", "usename.firstname.vietnam");
    }
}
