package com.ps.mulesoftconnector.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
@ToString
public class Response  {

	
	String access_token;
	String token_type;
	String redirectUrl;
	
	
}
