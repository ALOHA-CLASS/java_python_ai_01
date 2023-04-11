package com.human.project.util;

import java.lang.annotation.*;
import javax.validation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { UniqueUserValidator.class })
public @interface Unique {
    String message() default "중ㅋ벅ㅋ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> domainClass();

    String email();
}