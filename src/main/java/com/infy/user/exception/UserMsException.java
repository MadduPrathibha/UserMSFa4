package com.infy.user.exception;

@SuppressWarnings("serial")
public class UserMsException extends Throwable {

	private int statusCode;

	public UserMsException(String message) {
		super(message);
	}

	public UserMsException(String message, int statusCode) {
		super(message);
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
}