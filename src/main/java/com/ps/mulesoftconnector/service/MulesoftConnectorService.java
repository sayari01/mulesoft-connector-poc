package com.ps.mulesoftconnector.service;

import com.ps.mulesoftconnector.model.MulesoftConnectorRequest;

public interface MulesoftConnectorService {

	public String getApiDetails(MulesoftConnectorRequest request) throws Exception;
}
