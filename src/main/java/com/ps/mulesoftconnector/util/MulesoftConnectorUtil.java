package com.ps.mulesoftconnector.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

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

}
