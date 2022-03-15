package com.ps.mulesoftconnector.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@ToString
public class MessageResponse {
	
	    @JsonProperty("severity")
	    public String severity;
	    @JsonProperty("description")
		public String description;

}
