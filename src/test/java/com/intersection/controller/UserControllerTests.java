package com.intersection.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.intersection.model.User;
import com.intersection.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTests extends AbstractContextControllerTests {
	private MockMvc mockMvc;

	@Autowired
	Environment environment;

	@Autowired
	UserService userService;
	
	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(this.wac).alwaysExpect(
				status().isOk()).build();
	}

	@Test
	public void addUser() throws Exception {
		
		User user = new User();
		user.setPhoneId("1");
		user.setPushYn("Y");
		user.setCreated(new Timestamp((new Date()).getTime()));
		user.setModified(new Timestamp((new Date()).getTime()));
		
		userService.addUser(user);
	}
//
//	// @Test
//	public void ajaxArticle() throws Exception {
//		this.printJson("/get.rec?no=6");
//	}
//
//	@Test
//	public void listBoard() {
//		List<Board> boards = boardService.getBoards();
//		for (int i = 0; i < boards.size(); i++)
//			System.out.println(boards.get(i));
//
//	}

	public <T> void printJson(String url) {
		try {
			this.mockMvc.perform(get(url)).andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
