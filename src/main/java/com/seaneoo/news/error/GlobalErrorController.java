package com.seaneoo.news.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalErrorController implements ErrorController {

	@RequestMapping("/error")
	public ResponseEntity<ExceptionResponse> handleErrorMapping(HttpServletRequest req, HttpServletResponse res) {
		var httpStatus = HttpStatus.valueOf(res.getStatus());
		var response = ExceptionResponse.builder()
			.statusCode(httpStatus.value())
			.statusReason(httpStatus.getReasonPhrase())
			.path(req.getRequestURI())
			.build();
		return ResponseEntity.status(httpStatus).body(response);
	}
}
