package ru.otr.learn.http.handler;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(Exception.class)
	public String handleException(Exception e, @NotNull Model model) {
		String rootMessage = Throwables.getRootCause(e).getMessage();
		model.addAttribute("errorMessage", rootMessage);
		if (!log.isDebugEnabled() || e instanceof AccessDeniedException) {
			log.error("Exception: {}", rootMessage);
		} else {
			log.error(String.format("Exception: %s", rootMessage), e);
		}
		return "error/error-page";
	}
}
