package codegym.vn.validators;

import codegym.vn.model.User;
import codegym.vn.utils.UsernameValidationUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UsernameValidationUtils.mustBeVietnameseFirstname(errors, "username", "username.firstname.vietnam");
    }
}
