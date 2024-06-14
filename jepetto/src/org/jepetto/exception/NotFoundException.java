package org.jepetto.exception;

public class NotFoundException extends Exception {
	
	private String errorCode;
	private String keyword;
	
	public NotFoundException(Exception e, String keyword) {
		this.keyword = keyword;
		this.errorCode = "404";
	}
	
	public String getMessage() {
		return "'" + keyword + "'" + " not found" ;
	}
	

}
