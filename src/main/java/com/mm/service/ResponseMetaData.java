package com.mm.service;

public class ResponseMetaData {

	private String Command, requestId,responseTime;
	
	private Integer commandSeqId, totalResponsesAvailable, responseSequenceId;
	
	private boolean responseSanitizationRequired, completed;

	public String getCommand() {
		return Command;
	}

	public void setCommand(String command) {
		Command = command;
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
