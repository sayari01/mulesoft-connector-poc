package com.ps.mulesoftconnector.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ps.mulesoftconnector.model.CageResponse;
import com.ps.mulesoftconnector.model.Details;
import com.ps.mulesoftconnector.model.Message;
import com.ps.mulesoftconnector.model.Report;
import com.ps.mulesoftconnector.model.Reports;

@SpringBootTest 
@ExtendWith(SpringExtension.class)
public class MulesoftConnectorUtilTest {
	
	@InjectMocks
	private MulesoftConnectorUtil mulesoftConnectorUtil;

	@Test
	void categoryTest() throws Exception{
		CageResponse request = new CageResponse();
		Report report = new Report();
		report.setBranch("master");
		report.setGitUrl("link");
		report.setQuality("a");
		List<Reports> reportsList = new ArrayList<>();
		Reports reports = new Reports();
		reports.setRaml("a.raml");
		reports.setVersion("1.0.0");
		Details details = new Details();
		details.setNoOfErr(3);
		details.setNoOfInfo(5);
		details.setNoOfWarn(2);
		details.setTotal(10);
		List<Message> messageList = new ArrayList<>();
		Message message = new Message();
		Message message1 = new Message();
		Message message2 = new Message();
		Message message3 = new Message();
		Message message4 = new Message();
		Message message5 = new Message();
		message.setCode("aim-response-body-http-status-code-checks");
		message.setSeverity("Info");
		message.setMessage("This is Info1");
		message1.setCode("abcg");
		message1.setSeverity("Error");
		message1.setMessage("Error");
		message2.setCode("aim-response-body-http-status-code-checks");
		message2.setSeverity("warn");
		message2.setMessage("This is warn1");
		message3.setCode("aim-response-body-http-status-code-checks");
		message3.setSeverity("Error");
		message3.setMessage("2XX response code is not present");
		message4.setCode("abcr");
		message4.setSeverity("Error");
		message4.setMessage("error");
		message5.setCode("aim-response-body-http-status-code-checks");
		message5.setSeverity("Error");
		message5.setMessage("5XX response code is not present");
		messageList.add(message);
		messageList.add(message1);
		messageList.add(message2);
		messageList.add(message3);
		messageList.add(message4);
		messageList.add(message5);
		details.setMessages(messageList );
		reports.setDetails(details );
		reportsList.add(reports );
		report.setReports(reportsList);
		request.setReport(report);
		String result = mulesoftConnectorUtil.checkCriteriaForResponseCode(request, "aim-response-body-http-status-code-checks");
		Assertions.assertEquals("Fail", result);
		
	}

	@Test
	void checkCriteriaForSensitiveUriOrParamsSuccess() throws Exception{
		CageResponse request = new CageResponse();
		Report report = new Report();
		report.setBranch("master");
		report.setGitUrl("link");
		report.setQuality("a");
		List<Reports> reportsList = new ArrayList<>();
		Reports reports = new Reports();
		reports.setRaml("sample.raml");
		reports.setVersion("1.0.0");
		Details details = new Details();
		details.setNoOfErr(3);
		details.setNoOfInfo(5);
		details.setNoOfWarn(2);
		details.setTotal(10);
		List<Message> messageList = new ArrayList<>();
		Message message = new Message();
		Message message1 = new Message();
		Message message2 = new Message();
		Message message3 = new Message();
		Message message4 = new Message();
		Message message5 = new Message();
		message.setCode("samplerule1");
		message.setSeverity("Info");
		message.setMessage("No Sensitive information in Uri");
		message1.setCode("samplerule9");
		message1.setSeverity("Info");
		message1.setMessage("Error");
		message2.setCode("Samplerule5");
		message2.setSeverity("warn");
		message2.setMessage("This is warn1");
		message3.setCode("samplerule2");
		message3.setSeverity("Info");
		message3.setMessage("No Sensitive information in Parans");
		message4.setCode("samplerule3");
		message4.setSeverity("Error");
		message4.setMessage("error");
		message5.setCode("samplerule1");
		message5.setSeverity("Info");
		message5.setMessage("Nothing sensitive info found");
		messageList.add(message);
		messageList.add(message1);
		messageList.add(message2);
		messageList.add(message3);
		messageList.add(message4);
		messageList.add(message5);
		details.setMessages(messageList );
		reports.setDetails(details );
		reportsList.add(reports );
		report.setReports(reportsList);
		request.setReport(report);
		List<String> ruleList = new ArrayList<>();
		ruleList.add("samplerule1");
		ruleList.add("samplerule2");
		String result = mulesoftConnectorUtil.checkCriteriaForSensitiveUriOrParams(request, ruleList);
		Assertions.assertEquals("Pass", result);
		
	}
	
	@Test
	void checkCriteriaForSensitiveUriOrParamsWarn() throws Exception{
		CageResponse request = new CageResponse();
		Report report = new Report();
		report.setBranch("master");
		report.setGitUrl("link");
		report.setQuality("a");
		List<Reports> reportsList = new ArrayList<>();
		Reports reports = new Reports();
		reports.setRaml("sample.raml");
		reports.setVersion("1.0.0");
		Details details = new Details();
		details.setNoOfErr(3);
		details.setNoOfInfo(5);
		details.setNoOfWarn(2);
		details.setTotal(10);
		List<Message> messageList = new ArrayList<>();
		Message message = new Message();
		Message message1 = new Message();
		Message message2 = new Message();
		Message message3 = new Message();
		Message message4 = new Message();
		Message message5 = new Message();
		message.setCode("samplerule1");
		message.setSeverity("Info");
		message.setMessage("No Sensitive information in Uri");
		message1.setCode("samplerule1");
		message1.setSeverity("warn");
		message1.setMessage("Uri may contain sensitive information");
		message2.setCode("Samplerule5");
		message2.setSeverity("warn");
		message2.setMessage("This is warn1");
		message3.setCode("samplerule2");
		message3.setSeverity("Info");
		message3.setMessage("No Sensitive information in Parans");
		message4.setCode("samplerule2");
		message4.setSeverity("warn");
		message4.setMessage("Params may contain sensitive information");
		message5.setCode("samplerule1");
		message5.setSeverity("Info");
		message5.setMessage("Nothing sensitive info found");
		messageList.add(message);
		messageList.add(message1);
		messageList.add(message2);
		messageList.add(message3);
		messageList.add(message4);
		messageList.add(message5);
		details.setMessages(messageList );
		reports.setDetails(details );
		reportsList.add(reports );
		report.setReports(reportsList);
		request.setReport(report);
		List<String> ruleList = new ArrayList<>();
		ruleList.add("samplerule1");
		ruleList.add("samplerule2");
		String result = mulesoftConnectorUtil.checkCriteriaForSensitiveUriOrParams(request, ruleList);
		Assertions.assertEquals("Warn", result);
		
	}
	
	@Test
	void checkCriteriaForSensitiveUriOrParamsFail() throws Exception{
		CageResponse request = new CageResponse();
		Report report = new Report();
		report.setBranch("master");
		report.setGitUrl("link");
		report.setQuality("a");
		List<Reports> reportsList = new ArrayList<>();
		Reports reports = new Reports();
		reports.setRaml("sample.raml");
		reports.setVersion("1.0.0");
		Details details = new Details();
		details.setNoOfErr(3);
		details.setNoOfInfo(5);
		details.setNoOfWarn(2);
		details.setTotal(10);
		List<Message> messageList = new ArrayList<>();
		Message message = new Message();
		Message message1 = new Message();
		Message message2 = new Message();
		Message message3 = new Message();
		Message message4 = new Message();
		Message message5 = new Message();
		message.setCode("samplerule1");
		message.setSeverity("warn");
		message.setMessage("Uri may contain this Sensitive information");
		message1.setCode("samplerule1");
		message1.setSeverity("Error");
		message1.setMessage("Uri contains sensitive information");
		message2.setCode("Samplerule5");
		message2.setSeverity("warn");
		message2.setMessage("This is warn1");
		message3.setCode("samplerule2");
		message3.setSeverity("Error");
		message3.setMessage("Params contain sensitive information");
		message4.setCode("samplerule2");
		message4.setSeverity("warn");
		message4.setMessage("Params may contain sensitive information");
		message5.setCode("samplerule1");
		message5.setSeverity("Info");
		message5.setMessage("Nothing sensitive info found");
		messageList.add(message);
		messageList.add(message1);
		messageList.add(message2);
		messageList.add(message3);
		messageList.add(message4);
		messageList.add(message5);
		details.setMessages(messageList );
		reports.setDetails(details );
		reportsList.add(reports );
		report.setReports(reportsList);
		request.setReport(report);
		List<String> ruleList = new ArrayList<>();
		ruleList.add("samplerule1");
		ruleList.add("samplerule2");
		String result = mulesoftConnectorUtil.checkCriteriaForSensitiveUriOrParams(request, ruleList);
		Assertions.assertEquals("Fail", result);
		
	}
}

