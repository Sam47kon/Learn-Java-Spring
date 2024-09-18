package ru.otr.learn.http.handler;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice(basePackages = "ru.otr.learn.http.rest")
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
		String rootMessage = Throwables.getRootCause(e).getMessage();
		if (log.isDebugEnabled()) {
			log.error(String.format("Exception: %s", rootMessage), e);
		} else {
			log.error("Exception: {}", rootMessage);
		}
		return ResponseEntity.badRequest().body(rootMessage);
	}
}
