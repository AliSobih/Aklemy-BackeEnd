package com.e_learning.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class InvalidUserNameOrPasswordException extends RuntimeException{

	private static final long serialVersionUID = 316948179517070191L;

	private String field;
	public InvalidUserNameOrPasswordException(String field, String Message )
	{
		super(Message);
		this.field=field;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

}
