
package com.ps.mulesoftconnector.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.ps.mulesoftconnector.model.AuthenticationRequest;
import com.ps.mulesoftconnector.model.MulesoftConnectorRequest;
import com.ps.mulesoftconnector.model.Response;
import com.ps.mulesoftconnector.util.MulesoftConnectorUtil;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class MulesoftConnectorServiceImplWebclient implements MulesoftConnectorService {

	@Autowired
	MulesoftConnectorUtil mulesoftConnectorUtil;
	
	@Value("${mulesoft.link1}")
	private String link1;
	
	@Value("${mulesoft.link2}")
	private String link2;
	
	@Value("${mulesoft.link3}")
	private String link3;
	
	@Value("${mulesoft.paramLink}")
	private String paramLink;
	
	@Value("${mulesoft.referer}")
	private String refererLink;

	@Override
	public String getApiDetails(MulesoftConnectorRequest request) throws Exception {
		String samlResponse = getSamlResponse(request);
		String saml = mulesoftConnectorUtil.extractSaml(samlResponse);
		String authToken = getToken(saml);
		String apiDetails = getApiInfo(request, authToken);
		return apiDetails;
	}

	private String getSamlResponse(MulesoftConnectorRequest request) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(link1).queryParam("loginToRp", paramLink);
		log.info("The uri to be called {}", builder.toUriString());
		MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
		requestMap.add("AuthMethod", request.getAuthMethod());
		requestMap.add("UserName", request.getDomain() + "\\" +request.getUsername());
		requestMap.add("Password", request.getPassword());
		WebClient client = WebClient.create();
		String response = client.post().uri(builder.toUriString()).header("Referer", refererLink)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED).accept(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromFormData(requestMap)).retrieve()
				.onStatus(HttpStatus::is4xxClientError, error -> Mono.error(new RuntimeException("API not found")))
				.onStatus(HttpStatus::is5xxServerError,
						error -> Mono.error(new RuntimeException("Server is not responding")))
				.bodyToMono(String.class).block();
		return response;
	}

	private String getToken(String saml) {
		AuthenticationRequest authenticationRequest = new AuthenticationRequest();
		authenticationRequest.setSaml(saml);
		WebClient client = WebClient.create(link2);
		Response response = client.post().header("X-Requested-With", "XMLHttpRequest").bodyValue(authenticationRequest)
				.retrieve()
				.onStatus(HttpStatus::is4xxClientError, error -> Mono.error(new RuntimeException("API not found")))
				.onStatus(HttpStatus::is5xxServerError,
						error -> Mono.error(new RuntimeException("Server is not responding")))
				.bodyToMono(Response.class).block();

		return response.getAccess_token();
	}

	private String getApiInfo(MulesoftConnectorRequest request, String
			  authToken) {
		WebClient client = WebClient.create(link3 + request.getGroupId() + "/" + request.getApiId() + "/" +  request.getApiVersion() + "/");
		String response = client.get().header("Authorization", "bearer " + authToken)
				.retrieve()
				.onStatus(HttpStatus::is4xxClientError, error -> Mono.error(new RuntimeException("API not found")))
				.onStatus(HttpStatus::is5xxServerError,
				error -> Mono.error(new RuntimeException("Server is not responding")))
				.bodyToMono(String.class).block();
		File file = new File("C://Users/sayadhik/Documents/download.yaml");
		mulesoftConnectorUtil.downloadFile(response, file);
		return response;
	}

}
