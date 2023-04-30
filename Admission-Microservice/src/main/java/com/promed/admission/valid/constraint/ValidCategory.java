package com.promed.admission.valid.constraint;

import java.lang.annotation.Retention;

import com.promed.admission.valid.CategoryValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { CategoryValidator.class })
@Documented
public @interface ValidCategory {

	public abstract String max() default "";

	public abstract String message() default "Category is one of the valid options - Normal, Inpatient, Emergency, Outpatient";

	public abstract Class<?>[] groups() default {};

	public abstract Class<? extends Payload>[] payload() default {};
}