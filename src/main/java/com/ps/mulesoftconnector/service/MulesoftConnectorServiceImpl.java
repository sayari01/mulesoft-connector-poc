package com.ps.mulesoftconnector.service;

import java.io.File;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ps.mulesoftconnector.model.AuthenticationRequest;
import com.ps.mulesoftconnector.model.MulesoftConnectorRequest;
import com.ps.mulesoftconnector.model.Response;
import com.ps.mulesoftconnector.util.MulesoftConnectorUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MulesoftConnectorServiceImpl implements MulesoftConnectorService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	MulesoftConnectorUtil mulesoftConnectorUtil;

	@Override
	public String getApiDetails(MulesoftConnectorRequest request) throws Exception {
		ResponseEntity<String> samlResponse = getSamlResponse(request);
		if (samlResponse.getStatusCode().equals(HttpStatus.OK)) {
			String saml = mulesoftConnectorUtil.extractSaml(samlResponse);
			String authToken = getToken(saml);
			String apiDetails = getHtml(request, authToken);
			return apiDetails;
		}
		return "Unsuccessfull Request!!";

	}

	private ResponseEntity<String> getSamlResponse(MulesoftConnectorRequest request) throws Exception {
		String uri = "http://localhost:8081/link1";
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uri).queryParam("loginToRp", "link");
		log.info("The uri to be called {}", builder.toUriString());
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("Referer", "link");
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
		requestMap.add("AuthMethod", request.getAuthMethod());
		requestMap.add("UserName", request.getUsername());
		requestMap.add("Password", request.getPassword());

		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(requestMap, headers);

		try {
			ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity,
					String.class);
			log.info("Returned Saml Response Successfully!!");
			return response;
		} catch (Exception e) {
			throw new Exception("Error while fetching Saml Response:" + e.getMessage());
		}
	}

	private String getToken(String samlResponse) throws Exception {
		String uri = "http://localhost:8081/link2";
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Requested-With", "XMLHttpRequest");
		AuthenticationRequest authenticationRequest = new AuthenticationRequest();
		authenticationRequest.setSaml(samlResponse);
		HttpEntity<AuthenticationRequest> entity = new HttpEntity<AuthenticationRequest>(authenticationRequest,
				headers);
		try {
			ResponseEntity<Response> response = restTemplate.exchange(uri, HttpMethod.POST, entity, Response.class);
			log.info("Returned Auth Token Successfully!!");
			System.out.println(response.getBody().getAccess_token());
			return response.getBody().getAccess_token();

		} catch (Exception e) {
			throw new Exception("Error while fetching Authentication Token:" + e.getMessage());
		}

	}

	/*
	 * private String getApiDetails(MulesoftConnectorRequest request, String token)
	 * throws Exception { String uri = "http://localhost:8081/link3/" +
	 * request.getGroupId() + "/" + request.getApiId() + "/" +
	 * request.getApiVersion() + "/portal"; HttpHeaders headers = new HttpHeaders();
	 * headers.add("Authorization", "bearer " + token); HttpEntity<Void>
	 * requestEntity = new HttpEntity<>(headers); File file = new
	 * File("C://Users/sayadhik/Documents/download.yaml"); try {
	 * ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET,
	 * requestEntity, String.class); mulesoftConnectorUtil.downloadFile(response,
	 * file); return response.getBody();
	 * 
	 * } catch (Exception e) { throw new
	 * Exception("Error while fetching Api Details:" + e.getMessage());
	 * 
	 * }
	 * 
	 * }
	 */
	
	private String getHtml(MulesoftConnectorRequest request, String token) throws Exception {
		String uri = "https://www.w3schools.com/html/"; 
		HttpHeaders headers = new HttpHeaders();
//		headers.add("Authorization", "bearer " + token);
		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
		File file = new File("C://Users/sayadhik/Documents/download.yaml");
		try {
			ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
			mulesoftConnectorUtil.downloadFile(response, file);
			System.out.println(response.getBody());
			return response.getBody();
			

		} catch (Exception e) {
			throw new Exception("Error while fetching Api Details:" + e.getMessage());

		}

	}

}
