package com.mm.service;

import java.util.List;

public interface MockUiService {

	public List<Object> sendMockCommandRequest(String command);
	
	public List<Object> sendMockCommandRequest(String command, Integer nooftimes);
}
