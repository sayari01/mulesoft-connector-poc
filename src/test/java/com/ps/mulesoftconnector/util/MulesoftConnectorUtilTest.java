package com.ps.mulesoftconnector.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
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
		message.setCode("abc");
		message.setSeverity("Info");
		message.setMessage("Info");
		message1.setCode("abcg");
		message1.setSeverity("Error");
		message1.setMessage("Error");
		message2.setCode("abc");
		message2.setSeverity("Info");
		message2.setMessage("Info");
		message3.setCode("abc");
		message3.setSeverity("Error");
		message3.setMessage("This is Erro1");
		message4.setCode("abcr");
		message4.setSeverity("Error");
		message4.setMessage("error");
		message5.setCode("abc");
		message5.setSeverity("Error");
		message5.setMessage("This is Error2");
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
		String result = mulesoftConnectorUtil.checkCriteriaForResponseCode(request, "abc");
		Assertions.assertEquals("Fail", result);
		
	}

	
}

