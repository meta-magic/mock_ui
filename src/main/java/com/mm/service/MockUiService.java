package com.mm.service;

import atg.taglib.json.util.JSONArray;

public interface MockUiService {

	public JSONArray sendMockCommandRequest(String command);
	
	public JSONArray sendMockCommandRequest(String command, Integer nooftimes);
}
