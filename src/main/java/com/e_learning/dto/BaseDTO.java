
package com.e_learning.dto;


public abstract class BaseDTO {
	private long id;
	public BaseDTO(){

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
