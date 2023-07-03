package com.psja.CheckOrchestration.common;

public final class SystemException extends RuntimeException{
	
	private String errorCode;
	private String errorMessage;
	
	public SystemException( Exception exp, String errorCode, String errorMessage ){
		super( exp );
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return this.errorCode;
	}
	public String getErrorMessage() {
		return this.errorMessage;
	}
	
}
