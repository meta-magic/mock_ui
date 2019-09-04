package com.mm.service;

public class Data {
	
	private ResponseMetaData ResponseMetaData;
	
	private Object ResponseData;
	
	private Object ResponseStatus;

	public ResponseMetaData getResponseMetaData() {
		return ResponseMetaData;
	}

	public void setResponseMetaData(ResponseMetaData responseMetaData) {
		ResponseMetaData = responseMetaData;
	}

	public Object getResponseData() {
		return ResponseData;
	}

	public void setResponseData(Object responseData) {
		ResponseData = responseData;
	}

	public Object getResponseStatus() {
		return ResponseStatus;
	}

	public void setResponseStatus(Object responseStatus) {
		ResponseStatus = responseStatus;
	}
}
