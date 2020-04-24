package com.lin.missyou.dto.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordEqual {

    String message() default "passwords are not equal";

    int min() default 4;

    int max() default 6;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
