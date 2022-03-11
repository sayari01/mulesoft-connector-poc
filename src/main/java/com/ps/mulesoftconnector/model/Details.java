
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
    "noOfErr",
    "noOfWarn",
    "noOfInfo",
    "messages",
    "total"
})
@Getter
@Setter
@ToString
public class Details {

    @JsonProperty("noOfErr")
    public Integer noOfErr;
    @JsonProperty("noOfWarn")
    public Integer noOfWarn;
    @JsonProperty("noOfInfo")
    public Integer noOfInfo;
    @JsonProperty("messages")
    public List<Message> messages = null;
    @JsonProperty("total")
    public Integer total;

}
