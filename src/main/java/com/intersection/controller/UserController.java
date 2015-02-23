package com.intersection.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.intersection.model.User;
import com.intersection.service.UserService;

@RequestMapping("/user")
@Controller
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	public ApplicationContext context;

	@Autowired
	public UserService userService;

	@RequestMapping(value = "/proc/{code}", method=RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody 
	public void process(@PathVariable("code") String code, @RequestBody User user) {
		System.out.println("code : " + code + " user : " + user.toString());
		
		if (code.equals("add")) {
			if (user.getPhoneId() != null){
				try {
					userService.addUser(user);
				} catch(Exception e){
					
				}
			}
		} else if (code.equals("remove")) {
			userService.removeUserByNo(user.getUserNo());
		}
	}
}
