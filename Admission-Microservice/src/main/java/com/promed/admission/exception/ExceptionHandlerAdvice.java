package com.promed.admission.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

	@ExceptionHandler(AdmissionNotFoundException.class)
	public ResponseEntity<Map<String, List<String>>> handleNotFoundException(AdmissionNotFoundException ex) {
		List<String> errors = Collections.singletonList(ex.getMessage());
		return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		List<ApiSubError> apiValidationErrors = new ArrayList<>();
		Optional<List<FieldError>> fieldErrors = Optional.ofNullable(ex.getBindingResult().getFieldErrors());
		if (fieldErrors.isPresent()) {
			ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
				apiValidationErrors.add(ApiValidationError.builder().field(fieldError.getField())
						.message(fieldError.getDefaultMessage()).object(fieldError.getObjectName())
						.rejectedValue(fieldError.getField()).build());
			});
		}
		ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder().errorCode(HttpStatus.BAD_REQUEST.toString())
				.errorMessage("Validation Errors").subErrors(apiValidationErrors).build();
		return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<ApiErrorResponse> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        String unsupported = "Unsupported content type: " + ex.getContentType();
        //String supported = "Supported content types: " + MediaType.toString(ex.getSupportedMediaTypes());
        List<ApiSubError> apiValidationErrors = new ArrayList<>();
		apiValidationErrors.add(ApiValidationError.builder()
				.message(unsupported.concat(", Supported format is application/json")).build());
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder().errorCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.toString())
				.errorMessage("Unsupported Media Type").subErrors(apiValidationErrors).build();
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

	@ExceptionHandler(value = HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public ResponseEntity<ApiErrorResponse> handleUnprosseasableMsgException(HttpMessageNotReadableException ex) {

		List<ApiSubError> apiValidationErrors = new ArrayList<>();
		Throwable mostSpecificCause = ex.getMostSpecificCause();
		if (mostSpecificCause != null) {
			String message = mostSpecificCause.getMessage();
			apiValidationErrors.add(ApiValidationError.builder()
					.message(message.concat(", Required format is yyyy-MM-dd")).field("Birth Date").build());

		}
		ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
				.errorCode(HttpStatus.UNPROCESSABLE_ENTITY.toString()).errorMessage("Unable to Process Entity")
				.subErrors(apiValidationErrors).build();
		return new ResponseEntity<>(apiErrorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	private Map<String, List<String>> getErrorsMap(List<String> errors) {
		Map<String, List<String>> errorResponse = new HashMap<>();
		errorResponse.put("errors", errors);
		return errorResponse;
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<List<String>> handleConstraintViolationException(ConstraintViolationException cve) {
		List<String> errorMessages = cve.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
				.toList();

		return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Map<String, List<String>>> handleGeneralExceptions(Exception ex) {
		List<String> errors = Collections.singletonList(ex.getMessage());
		return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(RuntimeException.class)
	public final ResponseEntity<Map<String, List<String>>> handleRuntimeExceptions(RuntimeException ex) {
		List<String> errors = Collections.singletonList(ex.getMessage());
		return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<String> handleMissingParams(MissingServletRequestParameterException ex) {
		String name = ex.getParameterName();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parameter '" + name + "' is missing");

	}

	@ExceptionHandler(MissingPathVariableException.class)
	public ResponseEntity<String> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String error = ex.getParameter() + " parameter is missing";

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

	}

}
//