package com.mm.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger log = LoggerFactory.getLogger(MockUiServiceImpl.class);

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
		log.debug("Command Received {} to execute, will be executed {} times", command, nooftimes);
		
		for(int i=0; i<nooftimes; i++){
			String strRequest = this.getRequest(command);
			log.info("Sending request with body as {}", strRequest);
			this.sendRequest(strRequest);
			
		}
		

	}
	
	private void sendRequest(String requestPayload){
		
		if(littyUrl == null){
			log.info("Litty url is no defined {} ");
		}
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("initial","true");
		map.add("data",requestPayload);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		HttpEntity<MultiValueMap<String, String>> entity =
			    new HttpEntity<MultiValueMap<String, String>>(map, headers);
		
		ResponseEntity<Object> response = restTemplate.exchange(littyUrl, HttpMethod.POST, entity, Object.class);
		
		log.info("Response body {} ",response.getBody());
		
	}
	
	

	private String getRequest(String command) {
		StringBuilder sb = new StringBuilder();
		String line;

		if (fileLocation == null) {
			log.error("File lcoation is not defined {} ", fileLocation);
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
