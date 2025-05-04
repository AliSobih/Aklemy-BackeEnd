package com.e_learning.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class ItemNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 2426256104172639927L;
	public ItemNotFoundException(long Id)
	{
		super("Item  : " + Id + " doesn't exists");
	}
}
