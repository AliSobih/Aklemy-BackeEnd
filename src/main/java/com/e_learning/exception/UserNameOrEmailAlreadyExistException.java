package com.e_learning.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class UserNameOrEmailAlreadyExistException extends RuntimeException{
	private static final long serialVersionUID = 8517578182068814232L;
	
	public UserNameOrEmailAlreadyExistException(String username , String password)
	{
		super("Username  : " + username + " or Email : " + password + " already exists");
	}

}
