package com.exerciseBonus.annotations;

import com.exerciseBonus.validators.EmailValidator;
import org.springframework.stereotype.Component;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Component
@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EmailValidate {
    String message () default "Invalid email!";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
