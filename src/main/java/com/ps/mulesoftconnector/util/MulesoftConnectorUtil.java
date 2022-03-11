package com.ps.mulesoftconnector.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.ps.mulesoftconnector.model.CageResponse;
import com.ps.mulesoftconnector.model.Details;
import com.ps.mulesoftconnector.model.Message;
import com.ps.mulesoftconnector.model.Report;
import com.ps.mulesoftconnector.model.Reports;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MulesoftConnectorUtil {

	public String extractSaml(ResponseEntity<String> samlResponse) {
		Document document = Jsoup.parse(samlResponse.getBody());
		String resp = document.getElementsByTag("input").get(0).val();
		System.out.println("Saml Response: " + resp);
		return resp;
	}

	public void downloadFile(ResponseEntity<String> response, File file) {
		try {

			String resp = response.getBody();
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(resp);
			bw.close();
			// System.out.println("Done writing to " + fileName); //For testing
		} catch (IOException e) {
			System.out.println("Error: " + e);
			e.printStackTrace();
		}
	}

	public String checkCriteriaForResponseCode(CageResponse request, String ruleName) throws Exception {
		List<String> respList = new ArrayList<>();
		Report report = request.getReport();
		List<Reports> reportsList = report.getReports();
		for (Reports reports : reportsList) {
			Details details = reports.getDetails();
			List<Message> messageList = details.getMessages();
			for (Message message : messageList) {
				String code = message.getCode();
				if (code.equals(ruleName)) {

					respList.add(message.getSeverity());
				}
			}

		}
		if (respList.contains("Error")) {
			return "Fail";
		}
		if (respList.contains("warn")) {
			return "Warn";
		}
		if (!respList.contains("Error") && !respList.contains("warn")) {
			return "Pass";
		}
		return null;
	}
}
