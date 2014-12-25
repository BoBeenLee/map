package com.nexters.map;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the application home page.
 */
@Controller
@PropertySource("classpath:settings.properties")
public class MapController {
	private static final Logger logger = LoggerFactory
			.getLogger(MapController.class);
	private static final String apiKeyName = "daum.serverkey";
	
	@Autowired
	ApplicationContext context;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate);
		return "home";
	}

	@RequestMapping(value = "/daum", method = RequestMethod.GET)
	public ModelAndView daum() {
		ModelAndView mv = new ModelAndView();
//		logger.info("property : " + apiKey);
		logger.info("key " + context.getEnvironment().getProperty(apiKeyName));
		mv.addObject("apikey", context.getEnvironment().getProperty(apiKeyName));
		mv.setViewName("/map/daum");
		return mv;
	}
}

/*
 * property key 값 불러오기
 * - @PropertySource 세팅값 불러오고 @Value 를 이용하여 ${} 값 대입하기
 * - ApplicationContext 객체를 불러 getEnvironment().getProperty에 해당 속성 불러오기
*/