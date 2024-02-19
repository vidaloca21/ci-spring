package com.cafe.bbs.exceptions;

public class PageNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2115152855305143231L;
	
	public PageNotFoundException(String message) {
		super(message);
	}
}
