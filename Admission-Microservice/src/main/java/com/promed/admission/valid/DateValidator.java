package com.promed.admission.valid;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import com.promed.admission.valid.constraint.ValidBirthDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<ValidBirthDate, LocalDate> {


	static String dateFormat = "yyyy-MM-dd";

	@Override
	public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
		if (isDateFuture(value.format(formatter), dateFormat))
			return false;
		return true;
	}

	public boolean isDateFuture(final String date, final String dateFormat) {
		LocalDate localDate = LocalDate.now(ZoneId.systemDefault());
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateFormat);
		LocalDate inputDate = LocalDate.parse(date, dtf);
		return inputDate.isAfter(localDate);
	}

}
