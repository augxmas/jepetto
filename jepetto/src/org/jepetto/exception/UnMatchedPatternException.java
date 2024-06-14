package org.jepetto.exception;

public class UnMatchedPatternException extends Exception {

	private String message;
	private String errorCode;
	
	public UnMatchedPatternException(String errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}
	
	public UnMatchedPatternException() {
		this.errorCode = "501";
		this.message = "Unmatched Pattern";
	}
	
	
	public String getMessage() {
		return message;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
}
