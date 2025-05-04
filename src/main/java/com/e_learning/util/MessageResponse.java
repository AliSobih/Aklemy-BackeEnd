package com.e_learning.util;

public class MessageResponse {
	private String message;
	private String field;


	public MessageResponse(String field, String message) {
		super();
		this.message = message;
		this.field=field;
	}
	public MessageResponse( String message) {
		super();
		this.message = message;

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
}
