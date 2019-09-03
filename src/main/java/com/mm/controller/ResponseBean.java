package com.mm.controller;

public class ResponseBean {

	public boolean success;
	
	public Object data;
	
	

	public ResponseBean(boolean success, Object data) {
		super();
		this.success = success;
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public Object getData() {
		return data;
	}
	
}
