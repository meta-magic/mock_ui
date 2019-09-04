package com.mm.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;

@Service
@RequestScope
public class MockUiServiceImpl implements MockUiService {

	@Value("${litty.url}")
	private String littyUrl;

	@Value("${request.location}")
	private String fileLocation;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<Object> sendMockCommandRequest(String command) {
		return this.sendMockCommandRequest(command, 1);
	}

	@Override
	public List<Object> sendMockCommandRequest(String command, Integer nooftimes) {
		command = command.toLowerCase();
		System.out.println("Command Received " + command + " to execute, will be executed " + nooftimes + " times");
		List<Object> allresponse = new ArrayList<Object>();
		for (int i = 0; i < nooftimes; i++) {
			List list = this.sendRequest(command, 1);
			allresponse.addAll(list);
		}
		
		return allresponse;

	}

	private List<Object> sendRequest(String command, Integer seq) {

		String strRequest = this.getRequest(command + "_" + 1);
		System.out.println("Sending request with body as " + strRequest);
		try {
			return this.sendRequest(strRequest);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	private List<Object> sendRequest(String requestPayload) throws UnsupportedEncodingException {

		if (littyUrl == null) {
			System.out.println("Litty url is no defined {} ");
		}
		List<Object> allresponse = new ArrayList<Object>();
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		// map.add("initial","true");
		// map.add("data",requestPayload);

		String finalUrl = littyUrl + "?initial=true&data=" + URLEncoder.encode(requestPayload, "UTF-8");
		System.out.println("FINAL URL " + finalUrl);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		ResponseEntity<Object> response = restTemplate.exchange(finalUrl, HttpMethod.POST, entity, Object.class);
		allresponse.add(response.getBody());
		System.out.println("Response body " + response.getBody());
		System.out.println("CHECKING for multipleRequest");
		List<Object> otherResponse = this.multipleRequest(response.getBody().toString());
		allresponse.addAll(otherResponse);
		return allresponse;
		
	}

	private List<Object> multipleRequest(String body) {
		
		try {
			JSONObject json = new JSONObject(body);
			if (json.has("Data") && json.getJSONObject("Data").has("ResponseMetaData")) {
				JSONObject responseMetaData = json.getJSONObject("Data").getJSONObject("ResponseMetaData");
				String completed = responseMetaData.getString("completed");
				if (completed != null && completed.equals("false")) {
					if (responseMetaData.has("commandSeqId") && responseMetaData.has("totalResponsesAvailable")
							&& responseMetaData.has("Command")) {
						Integer commandSeqId = Integer.valueOf(responseMetaData.getString("commandSeqId"));
						String command = responseMetaData.getString("Command");

						commandSeqId++;
						return this.sendRequest(command, commandSeqId);
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	private String getRequest(String command) {
		StringBuilder sb = new StringBuilder();
		String line;

		if (fileLocation == null) {
			System.out.println("File lcoation is not defined " + fileLocation);
		}

		try {
			String fileName = fileLocation + File.separator + command + ".json";
			BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));

			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
