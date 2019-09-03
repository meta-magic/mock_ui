package com.mm.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
	public void sendMockCommandRequest(String command) {
		this.sendMockCommandRequest(command, 1);
	}

	@Override
	public void sendMockCommandRequest(String command, Integer nooftimes) {
		System.out.println("Command Received "+ command+" to execute, will be executed "+nooftimes+" times");
		
		for(int i=0; i<nooftimes; i++){
			String strRequest = this.getRequest(command);
			System.out.println("Sending request with body as "+ strRequest);
			try {
				this.sendRequest(strRequest);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		

	}
	
	private void sendRequest(String requestPayload) throws UnsupportedEncodingException{
		
		if(littyUrl == null){
			System.out.println("Litty url is no defined {} ");
		}
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		//map.add("initial","true");
		//map.add("data",requestPayload);
		
		String finalUrl = littyUrl +"?initial=true&data="+URLEncoder.encode(requestPayload, "UTF-8");
		System.out.println("FINAL URL "+finalUrl);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		HttpEntity<MultiValueMap<String, String>> entity =
			    new HttpEntity<MultiValueMap<String, String>>(map, headers);
		
		ResponseEntity<Object> response = restTemplate.exchange(finalUrl, HttpMethod.POST, entity, Object.class);
		
		System.out.println("Response body "+response.getBody());
		
	}
	
	

	private String getRequest(String command) {
		StringBuilder sb = new StringBuilder();
		String line;

		if (fileLocation == null) {
			System.out.println("File lcoation is not defined "+ fileLocation);
		}

		try {

			BufferedReader br = new BufferedReader(new FileReader(new File(fileLocation+File.separator+command+".json")));

			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
