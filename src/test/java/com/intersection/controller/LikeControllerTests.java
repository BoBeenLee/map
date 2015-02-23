package com.intersection.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.intersection.service.LikeService;

import org.springframework.http.converter.json.GsonHttpMessageConverter;

@RunWith(SpringJUnit4ClassRunner.class)
public class LikeControllerTests extends AbstractContextControllerTests {
	private MockMvc mockMvc;

	@Autowired
	Environment environment;

	@Autowired
	LikeService likeService;
	
	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(this.wac).alwaysExpect(
				status().isOk()).build();
	}
	
	@Test
	public void addLike(){
		likeService.addLike("1", 5);
	}
	
	
	 @Test
	 @Ignore
	public void test() throws Exception {
		this.printJson("/like/list.map?names=test&names=test1");
//		new GsonHttpMessageConverter() 
		
	}
	 
	 @Test
	 @Ignore
	 public void getLikesByPhoneId(){
		 System.out.println(likeService.getLikesByPhoneId("1", 3));
	 }
	 
	 @Test
	 @Ignore
	 public void getLikesByNames(){
		 List<String> names = new ArrayList<String>(){{
			 add("°¡´É");
		 }};
		 likeService.getLikesByNames(names, "");
	 }
	 
	public <T> void printJson(String url) {
		try {
			this.mockMvc.perform(get(url)).andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
