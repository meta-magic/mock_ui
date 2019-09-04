package com.mm.service;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseMetaData {

	@JsonProperty(value = "Command")
	private String command;
	@JsonProperty(value = "requestId")
	private String requestId;
	@JsonProperty(value = "responseTime")
	private String responseTime;
	@JsonProperty(value = "commandSeqId")
	private Integer commandSeqId;
	@JsonProperty(value = "totalResponsesAvailable")
	private Integer totalResponsesAvailable;
	@JsonProperty(value = "responseSequenceId")
	private Integer responseSequenceId;
	@JsonProperty(value = "responseSanitizationRequired")
	private boolean responseSanitizationRequired;
	
	@JsonProperty(value = "completed")
	private boolean completed;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}

	public Integer getCommandSeqId() {
		return commandSeqId;
	}

	public void setCommandSeqId(Integer commandSeqId) {
		this.commandSeqId = commandSeqId;
	}

	public Integer getTotalResponsesAvailable() {
		return totalResponsesAvailable;
	}

	public void setTotalResponsesAvailable(Integer totalResponsesAvailable) {
		this.totalResponsesAvailable = totalResponsesAvailable;
	}

	public Integer getResponseSequenceId() {
		return responseSequenceId;
	}

	public void setResponseSequenceId(Integer responseSequenceId) {
		this.responseSequenceId = responseSequenceId;
	}

	public boolean isResponseSanitizationRequired() {
		return responseSanitizationRequired;
	}

	public void setResponseSanitizationRequired(boolean responseSanitizationRequired) {
		this.responseSanitizationRequired = responseSanitizationRequired;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
 
}
