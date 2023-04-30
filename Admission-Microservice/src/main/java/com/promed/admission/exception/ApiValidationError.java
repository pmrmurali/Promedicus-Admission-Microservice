package com.promed.admission.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiValidationError extends ApiSubError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;
}
