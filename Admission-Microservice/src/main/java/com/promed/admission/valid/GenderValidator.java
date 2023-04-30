package com.promed.admission.valid;

import java.util.Arrays;

import com.promed.admission.valid.constraint.ValidGender;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<ValidGender, String> {




	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return Arrays.stream(Gender.values()).anyMatch((t) -> t.name().equals(value));
	}


	
	private enum Gender {
		Female, Male, Intersex, Unknown
	};

}
