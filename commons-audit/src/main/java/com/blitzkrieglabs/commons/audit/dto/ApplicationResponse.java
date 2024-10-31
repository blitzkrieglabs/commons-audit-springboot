package com.blitzkrieglabs.commons.audit.dto;

import java.util.HashMap;

import org.springframework.http.HttpStatusCode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;



@Setter
@Getter
@Builder
@JsonNaming(SnakeCaseStrategy.class)
public class ApplicationResponse {

	public static String HEADER_ERROR_FIELDS = "X-Error-Fields";
	public static String HEADER_ERROR_CODE = "X-Error-Code";
	public static String HEADER_ERROR_MESSAGE = "X-Error-Message";
	
	
	private HttpStatusCode status;
	
	@Builder.Default
	private transient HashMap<String,Object> headers = new HashMap<>();
	
	//typically the same as status code since it can handle most of the cases, but can contain custom error number for special handling
	private int code;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String message;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Object data;
	
	
	
}
