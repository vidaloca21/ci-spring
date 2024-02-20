package com.cafe.bbs.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler({RequestFailedException.class}) 
	public String viewErrorPage(RuntimeException runtimeException, Model model) {
		logger.warn(runtimeException.getMessage());
		model.addAttribute("msg", runtimeException.getMessage());
		return "error/error";
	}
	@ExceptionHandler({PageNotFoundException.class}) 
	public String viewPageNotFoundPage(RuntimeException runtimeException, Model model) {
		logger.warn(runtimeException.getMessage());
		model.addAttribute("msg", runtimeException.getMessage());
		return "error/error";
	}
	@ExceptionHandler({FileNameEncodingException.class, FileNotExistsException.class}) 
	public String viewFileErrorPage(RuntimeException runtimeException, Model model) {
		logger.warn(runtimeException.getMessage());
		model.addAttribute("msg", runtimeException.getMessage());
		return "error/error";
	}

}
