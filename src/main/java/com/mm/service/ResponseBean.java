package com.mm.service;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseBean {
	
	@JsonProperty(value = "Data")
	private Data data;

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
 
	
}
