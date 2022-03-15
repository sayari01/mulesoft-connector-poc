package com.ps.mulesoftconnector.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ps.mulesoftconnector.model.MulesoftConnectorRequest;
import com.ps.mulesoftconnector.service.MulesoftConnectorService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MulesoftConnectorController {

	@Autowired
	MulesoftConnectorService mulesoftConnectorService;

	@PostMapping(path = "/getapidetails", consumes = "application/json", produces = "application/json")
	private ResponseEntity<String> getApiDetailsResponse(@RequestBody MulesoftConnectorRequest request)
			throws Exception {
		log.info("Entering controller..");
		System.setProperty("http_proxy", "http://"+request.getUsername()+":"+request.getPassword()+"link");
		System.setProperty("https_proxy", "http://"+request.getUsername()+":"+request.getPassword()+"link");
		String apiDetails = mulesoftConnectorService.getApiDetails(request);
		return new ResponseEntity<String>(apiDetails, HttpStatus.OK);

	}

}
