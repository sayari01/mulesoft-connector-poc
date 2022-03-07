package com.ps.mulesoftconnector.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MulesoftConnectorRequest {
	
	String username;
	String password;
	String authMethod;
	String domain;
	String groupId;
	String apiId;
	String apiVersion;


}
