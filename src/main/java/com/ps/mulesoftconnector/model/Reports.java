
package com.ps.mulesoftconnector.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "raml",
    "version",
    "details"
})
@Getter
@Setter
@ToString
public class Reports {

    @JsonProperty("raml")
    public String raml;
    @JsonProperty("version")
    public String version;
    @JsonProperty("details")
    public Details details;
   
}
