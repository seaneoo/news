package com.seaneoo.news.error;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
