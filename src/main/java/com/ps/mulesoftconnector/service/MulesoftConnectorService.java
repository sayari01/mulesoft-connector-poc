package com.ps.mulesoftconnector.service;

import org.springframework.stereotype.Service;

import com.ps.mulesoftconnector.model.MulesoftConnectorRequest;

@Service
public interface MulesoftConnectorService {

	public String getApiDetails(MulesoftConnectorRequest request) throws Exception;
}
