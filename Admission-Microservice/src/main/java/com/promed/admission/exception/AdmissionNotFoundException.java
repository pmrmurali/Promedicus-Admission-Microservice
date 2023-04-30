package com.promed.admission.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AdmissionNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2819601896398794348L;

	public AdmissionNotFoundException(String message) {
		super(message);
	}

	public AdmissionNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
