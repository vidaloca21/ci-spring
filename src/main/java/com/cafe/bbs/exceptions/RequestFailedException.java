package com.cafe.bbs.exceptions;

public class RequestFailedException extends RuntimeException {
	
	private static final long serialVersionUID = -144069918626360058L;
	
	public RequestFailedException(String message) {
		super(message);
	}

}
