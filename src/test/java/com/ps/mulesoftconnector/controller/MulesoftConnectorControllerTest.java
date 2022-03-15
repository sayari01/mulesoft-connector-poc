package com.ps.mulesoftconnector.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ps.mulesoftconnector.model.MulesoftConnectorRequest;
import com.ps.mulesoftconnector.service.MulesoftConnectorService;

@WebMvcTest
@ExtendWith(SpringExtension.class)
public class MulesoftConnectorControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@InjectMocks
	private MulesoftConnectorController mulesoftConnectorController;
	
	@MockBean
	private MulesoftConnectorService mulesoftConnectorService;
	
	@MockBean
	private MulesoftConnectorRequest request;

	@Test
	void getApiDetailsResponseTest() throws Exception {
		MulesoftConnectorRequest request = new MulesoftConnectorRequest();
		request.setApiId("/abc");
		when(mulesoftConnectorService.getApiDetails(request)).thenReturn("Success");
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/getapidetails")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n"
						+ "    \"username\": \"user\",\r\n"
						+ "    \"password\": \"pass\",\r\n"
						+ "    \"authMethod\":\"auth\",\r\n"
						+ "    \"domain\":\"org\",\r\n"
						+ "    \"groupId\":\"group\",\r\n"
						+ "    \"apiId\":\"abc\",\r\n"
						+ "    \"apiVersion\":\"1.0.0\"\r\n"
						+ "}"))
				.andExpect(status().isOk()).andReturn();
		Assertions.assertEquals(200, result.getResponse().getStatus());      
		
	}

}
