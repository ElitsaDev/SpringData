package com.exerciseBonus.annotations;

import com.exerciseBonus.validators.PasswordValidator;
import org.springframework.stereotype.Component;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Component
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PasswordValidate {
    String message() default "Invalid password!";
    int minLength() default 6;
    int maxLength() default 30;
    boolean containsLowercase() default false;
    boolean containsUppercase() default false;
    boolean containsDigit() default false;
    boolean containsSpecialSymbols() default false;

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
