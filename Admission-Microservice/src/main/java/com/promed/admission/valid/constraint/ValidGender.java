package com.promed.admission.valid.constraint;

import java.lang.annotation.Retention;
import com.promed.admission.valid.GenderValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { GenderValidator.class })
@Documented
public @interface ValidGender {

	public abstract String max() default "";

	public abstract String message() default "Sex is one of the valid options - Female, Male, Intersex, Unknown ";

	public abstract Class<?>[] groups() default {};

	public abstract Class<? extends Payload>[] payload() default {};
}