package com.cafe.bbs.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler({IncorrectPasswordException.class, RequestFailedException.class}) 
	public String viewErrorPage(RuntimeException runtimeException, Model model) {
		model.addAttribute("msg", runtimeException.getMessage());
		return "error/error";
	}
	@ExceptionHandler({PageNotFoundException.class}) 
	public String viewPageNotFoundPage(RuntimeException runtimeException, Model model) {
		model.addAttribute("msg", runtimeException.getMessage());
		return "error/error";
	}
	@ExceptionHandler({FileNameEncodingException.class, FileNotExistsException.class}) 
	public String viewFileErrorPage(RuntimeException runtimeException, Model model) {
		model.addAttribute("msg", runtimeException.getMessage());
		return "error/error";
	}

}
