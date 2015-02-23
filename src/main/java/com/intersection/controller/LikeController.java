package com.intersection.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.intersection.model.Like;
import com.intersection.model.Translation;
import com.intersection.service.LikeService;
import com.intersection.service.TransService;
import com.intersection.service.UserService;

@RequestMapping("/like")
@Controller
public class LikeController {
	private static final Logger logger = LoggerFactory.getLogger(LikeController.class);

	@Autowired
	public ApplicationContext context;

	@Autowired
	public LikeService likeService;

	@Autowired
	public TransService transService;

	@Autowired
	public UserService userService;

	@RequestMapping(value = "/list", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<Translation> ajaxLikes(@RequestBody Map<String, Object> map) throws IOException {
		List<String> names = (List<String>) map.get("names");
		List<String> transNames = new ArrayList<String>();
		String phoneId = (String) map.get("phoneId");
		
		for(int i=0; i<names.size(); i++)
			transNames.add(i, URLDecoder.decode(names.get(i), "UTF-8"));
		List<Translation> translations = likeService.getLikesByNames(transNames, phoneId);
		
		System.out.println(transNames.toString());
		return translations;
	}

	@RequestMapping(value = "/proc/{code}", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public void process(@PathVariable("code") String code, @RequestBody Map<String, String> map) {
		// @RequestParam String phoneId, @RequestParam int transNo
		int transNo = Integer.parseInt(map.get("transNo"));
		String phoneId = (String) map.get("phoneId");
		
		Translation translation = transService.getTranslationByNo(transNo);

		System.out.println("process");
		
		if (code.equals("toggle")) {
			Like like = likeService.getLikesByPhoneId(phoneId, transNo);
			
			System.out.println("phoneId : " + phoneId + " - transNo : " + transNo);
			
			System.out.println("like : " + like);
			if(like == null)
				likeService.addLike(phoneId, transNo);
			else
				likeService.removeLikeByNo(like.getLikeNo());
		} else if (code.equals("remove")) {

		}
	}
}
