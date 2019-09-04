package com.mm.service;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {
	
	@JsonProperty(value = "ResponseMetaData")
	private ResponseMetaData responseMetaData;
	
	@JsonProperty(value = "ResponseData")
	private Object responseData;
	
	@JsonProperty(value = "ResponseStatus")
	private Object responseStatus;

	public ResponseMetaData getResponseMetaData() {
		return responseMetaData;
	}

	public void setResponseMetaData(ResponseMetaData responseMetaData) {
		this.responseMetaData = responseMetaData;
	}

	public Object getResponseData() {
		return responseData;
	}

	public void setResponseData(Object responseData) {
		this.responseData = responseData;
	}

	public Object getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(Object responseStatus) {
		this.responseStatus = responseStatus;
	}
	
	
 
}
