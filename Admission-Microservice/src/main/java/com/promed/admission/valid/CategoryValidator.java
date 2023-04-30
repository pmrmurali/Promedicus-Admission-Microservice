package com.promed.admission.valid;

import java.util.Arrays;

import com.promed.admission.valid.constraint.ValidCategory;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CategoryValidator implements ConstraintValidator<ValidCategory, String> {



	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return Arrays.stream(Category.values()).anyMatch((t) -> t.name().equals(value));
	}

	private enum Category {
		Normal, Inpatient, Emergency, Outpatient
	};

}
