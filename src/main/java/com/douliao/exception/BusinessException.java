package com.douliao.exception;

public class BusinessException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }
	
}
