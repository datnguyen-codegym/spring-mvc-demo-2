package codegym.vn.model;


import codegym.vn.utils.UsernameValidationUtils;
import lombok.Data;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Data
public class User {
    String username;
    int age;
    Gender gender;
}
