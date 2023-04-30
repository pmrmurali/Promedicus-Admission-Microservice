package com.promed.admission.exception;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ApiErrorResponse {
    private String errorCode;
    private String errorMessage;
    private List<ApiSubError> subErrors;
}
