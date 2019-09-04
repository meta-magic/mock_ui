package com.mm.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mm.service.MockUiService;

@RestController
@RequestMapping("/api/mockui")
public class MockUiController {

	@Value("${response.location}")
	private String responseLocation;
	
	@Autowired
	private MockUiService mockUiService;
	
	@GetMapping(value="/{command}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseBean> mockUiTest(@PathVariable("command") String command) {
		List<Object> list = mockUiService.sendMockCommandRequest(command);
		return new ResponseEntity<ResponseBean>(new ResponseBean(true, list), HttpStatus.OK);
	}
	
	@GetMapping("/{command}/{nooftimes}")
	public void mockUiTest(@PathVariable("command") String command, @PathVariable("nooftimes") Integer nooftimes) {
		mockUiService.sendMockCommandRequest(command, nooftimes);
	}
	
	@PostMapping(value="/dummylitty", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<String> dummyLitty(@RequestParam Map<String, String> requestParam){
		
		
		System.out.println("Initial "+ requestParam.get("initial")+" and Data  "+requestParam.get("data"));
		
		StringBuilder sb = new StringBuilder();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(responseLocation+File.separator+"mnrsearch_1.json")));
			String line = null;
			while((line = br.readLine())!=null){
				sb.append(line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<String>(sb.toString(), HttpStatus.OK);
	}

}
