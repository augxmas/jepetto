package org.jepetto.exception;

public class InvalidSizeException extends Exception {

	private String errorCode = "12899 ";
	private String message = null;
	
	public InvalidSizeException( int length, int valueLength) {
		message ="contraint size is " + length + " input value size is " +  valueLength;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public String getMessage() {
		return message;
	}	
	
}
