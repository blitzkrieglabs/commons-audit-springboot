package com.blitzkrieglabs.api.audit.validations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.blitzkrieglabs.commons.audit.dto.ApplicationResponse;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ParameterExceptionHandler {
	

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleRequiredParameters(ConstraintViolationException ex) {
    	
    	BodyBuilder res = ResponseEntity.status(HttpStatus.BAD_REQUEST);
        List<String> emptyFields = ex.getConstraintViolations().stream()
            .filter(violation -> violation.getConstraintDescriptor().getAnnotation() instanceof NotBlank)
            .map(violation -> {
                String propertyPath = violation.getPropertyPath().toString();
                
                if (propertyPath.contains(".")) {
                    propertyPath = propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
                }

                // Convert to snake case
                propertyPath = propertyPath.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
                return propertyPath;
            	}
            )
            .collect(Collectors.toList());

        if(emptyFields.size()>0) {
        	return res.header(ApplicationResponse.HEADER_ERROR_CODE, HttpStatus.BAD_REQUEST.value()+"")
    				.header(ApplicationResponse.HEADER_ERROR_FIELDS,String.join(",", emptyFields))
    	 			.header(ApplicationResponse.HEADER_ERROR_MESSAGE,"field should not be null or empty")
    	 			.build();	
        }
        
        
        //TODO: handle pattern validation
        return res.build();
        
    }
}
