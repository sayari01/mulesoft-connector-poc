
package com.ps.mulesoftconnector.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "reports",
    "gitUrl",
    "branch",
    "quality"
})
@Getter
@Setter
@ToString
public class Report {

    @JsonProperty("reports")
    public List<Reports> reports = null;
    @JsonProperty("gitUrl")
    public String gitUrl;
    @JsonProperty("branch")
    public String branch;
    @JsonProperty("quality")
    public String quality;

}
