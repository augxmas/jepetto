package org.jepetto.exception;

public class RequiredValueMissingException  extends Exception{
	
	private String errorCode = "01400 ";
	private String message = null;
	
	public RequiredValueMissingException(Exception e) {
		message ="it's not null but input value is null or not including any value";
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public String getMessage() {
		return message;
	}

}
