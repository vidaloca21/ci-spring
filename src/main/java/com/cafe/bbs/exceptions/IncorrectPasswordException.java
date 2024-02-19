package com.cafe.bbs.exceptions;

public class IncorrectPasswordException extends RuntimeException{

	private static final long serialVersionUID = 6066793019005014634L;
	
	public IncorrectPasswordException(String message) {
		super(message);
	}
}
