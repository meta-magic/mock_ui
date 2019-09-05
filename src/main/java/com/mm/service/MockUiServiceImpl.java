package com.mm.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import atg.taglib.json.util.JSONArray;
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
	public JSONArray sendMockCommandRequest(String command) {
		JSONArray allresponse = new JSONArray();
		this.sendRequest(true, command, 1,allresponse);
		return allresponse;
	}

	@Override
	public JSONArray sendMockCommandRequest(String command, Integer nooftimes) {
		System.out.println();
		command = command.toLowerCase();
		System.out.println("Command Received " + command + " to execute, will be executed " + nooftimes + " times");
		JSONArray allresponse = new JSONArray();
		for (int i = 0; i < nooftimes; i++) {
			this.sendRequest(true,command, 1,allresponse);
		}
		
		System.out.println();
		
		return allresponse;

	}

	private void sendRequest(boolean initial, String command, Integer seq, JSONArray allresponse) {

		String strRequest = this.getRequest(command + "_" + seq);
		System.out.println("Sending request with body as " + strRequest);
		try {
			this.sendRequest(initial, strRequest, allresponse);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private JSONArray sendRequest(boolean initial, String requestPayload, JSONArray allresponse) throws UnsupportedEncodingException {

		if (littyUrl == null) {
			System.out.println("Litty url is no defined {} ");
		}
		
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		// map.add("initial","true");
		// map.add("data",requestPayload);

		String finalUrl = littyUrl + "?initial="+initial+"&data=" + URLEncoder.encode(requestPayload, "UTF-8");
		System.out.println("Encoded URL " + finalUrl);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setConnection("Upgrade");
		headers.setUpgrade("HTTP/2.0");
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		ResponseEntity<String> response = restTemplate.exchange(finalUrl, HttpMethod.POST, entity, String.class);
		
		System.out.println("Response Received " + response.getBody());
		try {
			
			allresponse.add(new JSONObject(response.getBody()));
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		System.out.println();
		System.out.println("CHECKING for multipleRequest based on response received");
		 
		try {
			com.mm.service.ResponseBean responseBean = new ObjectMapper().readValue(response.getBody(), com.mm.service.ResponseBean.class);
			this.multipleRequest(responseBean, allresponse);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return allresponse;
		
	}

	private void multipleRequest(com.mm.service.ResponseBean body, JSONArray allresponse) {
		
		try {
			
			System.out.println("Response OBJECT is "+new ObjectMapper().writeValueAsString(body));
			if (body.getData()!=null && body.getData().getResponseMetaData()!=null) {
				ResponseMetaData responseMetaData = body.getData().getResponseMetaData();
				boolean completed = responseMetaData.isCompleted();
				System.out.println("Completed flag "+completed);
				if (!completed) {
					if (responseMetaData.getCommandSeqId()!=null && responseMetaData.getTotalResponsesAvailable()!=null
							&& responseMetaData.getCommand()!=null) {
						Integer commandSeqId = responseMetaData.getResponseSequenceId();
						String command = responseMetaData.getCommand();
						System.out.println("Command  "+command +" commandSeqId "+commandSeqId);
						commandSeqId++;
						Thread.sleep(2000);
						this.sendRequest(false, command, commandSeqId, allresponse);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String getRequest(String command) {
		StringBuilder sb = new StringBuilder();
		String line;

		if (fileLocation == null) {
			System.out.println("File lcoation is not defined " + fileLocation);
		}

		try {
			String fileName = fileLocation + File.separator + command + ".json";
			System.out.println("Reading request from file "+fileName);
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
