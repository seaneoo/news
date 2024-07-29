package com.seaneoo.news.error;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(GlobalErrorController.class);

	private String stackTraceAsString(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleGenericException(HttpServletRequest req, Exception e) {
		logger.warn(stackTraceAsString(e));
		var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		var response = ExceptionResponse.builder()
			.statusCode(httpStatus.value())
			.statusReason(httpStatus.getReasonPhrase())
			.path(req.getRequestURI())
			.message(e.getMessage())
			.build();
		return ResponseEntity.status(httpStatus).body(response);
	}

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ExceptionResponse> handleResponseStatusException(HttpServletRequest req, ResponseStatusException e) {
		var httpStatus = HttpStatus.valueOf(e.getStatusCode().value());
		var response = ExceptionResponse.builder()
			.statusCode(httpStatus.value())
			.statusReason(httpStatus.getReasonPhrase())
			.path(req.getRequestURI())
			.message(e.getReason())
			.build();
		return ResponseEntity.status(httpStatus).body(response);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException e) {
		var httpStatus = HttpStatus.BAD_REQUEST;

		ArrayList<String> errors = new ArrayList<>();
		var bindingResult = e.getBindingResult();
		for (var error : bindingResult.getAllErrors())
			errors.add(error.getDefaultMessage());

		var response = ExceptionResponse.builder()
			.statusCode(httpStatus.value())
			.statusReason(httpStatus.getReasonPhrase())
			.path(req.getRequestURI())
			.errors(errors)
			.build();
		return ResponseEntity.status(httpStatus).body(response);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ExceptionResponse> handleAuthenticationException(HttpServletRequest req, AuthenticationException e) {
		var httpStatus = HttpStatus.BAD_REQUEST;
		if (e instanceof BadCredentialsException)
			httpStatus = HttpStatus.UNAUTHORIZED;

		var response = ExceptionResponse.builder()
			.statusCode(httpStatus.value())
			.statusReason(httpStatus.getReasonPhrase())
			.path(req.getRequestURI())
			.message(e.getMessage())
			.build();
		return ResponseEntity.status(httpStatus).body(response);
	}
}
