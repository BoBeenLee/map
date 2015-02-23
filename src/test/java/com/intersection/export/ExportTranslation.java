package com.intersection.export;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.intersection.model.Translation;
import com.intersection.service.TransService;

@RunWith(SpringJUnit4ClassRunner.class)
public class ExportTranslation extends AbstractContextControllerTests {
	private MockMvc mockMvc;

	@Autowired
	Environment environment;

	@Autowired
	TransService transService;
	
	public static String[] list = new String[]{
		"01호선", "02호선", "03호선", "04호선", "05호선", "06호선","07호선", "08호선", "09호선",
		"인천선", "분당선", "중앙선", "공항철도","경의선", "신분당선", "경춘선","수인선"   
	};
	
	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(this.wac).alwaysExpect(
				status().isOk()).build();
	}
	
	public List<Translation> findTranslations(String url) throws IOException {
		List<Translation> translations = new ArrayList<Translation>();

		// URL 연결
		Connection con = HttpConnection.connect(url);
		Document doc = con.get();
		
		Elements elements = doc.select(".data-a tbody tr");
		
		for (Element elem : elements) {
//			System.out.println(elem.select("th a").html() + " - " + elem.select("td:nth-of-type(1)").html());
			Translation translation = new Translation();
			
			translation.setName(elem.select("th a").html() + "역");
			translation.setType(elem.select("td:nth-of-type(1)").html());
			translation.setCreated(new Timestamp((new Date()).getTime()));
			translation.setModified(new Timestamp((new Date()).getTime()));
			
			List<Translation> isExist = transService.getTranslationByName(translation.getName());
			
			if(isExist != null && isExist.size() > 0)
				transService.modifyTranslation(isExist.get(0).getType() + "," + translation.getType(), isExist.get(0).getTransNo());
			else
				transService.addTranslation(translation);
		}
		
		return translations;
	}
	
	@Test
	@Ignore
	public void insert(){
		
		Translation translation = new Translation();
		
		translation.setName("test");
		translation.setType("중앙선");
		
		transService.addTranslation(translation);
//		transactionService.
	}
	
	@Test
//	@Ignore
	public void search(){
		transService.getTranslationByName("test");
	}
	
	@Test
//	@Ignore
	public void export() throws IOException {
		
		for(String title : list){
//			System.out.println("title : " + title);
			findTranslations("http://www.smrt.co.kr/program/Train/Search/Search_station.jsp?lineKeyWord=" + title);
		}
	}
}
