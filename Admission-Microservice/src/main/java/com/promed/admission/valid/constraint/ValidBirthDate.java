package com.promed.admission.valid.constraint;

import java.lang.annotation.Retention;
import com.promed.admission.valid.DateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { DateValidator.class })
@Documented
public @interface ValidBirthDate {

	public abstract String max() default "";

	public abstract String message() default "Birth Date must not be in the future";

	public abstract Class<?>[] groups() default {};

	public abstract Class<? extends Payload>[] payload() default {};
}