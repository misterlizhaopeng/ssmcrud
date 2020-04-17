package com.lp.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes(value= { "list"})
public class TestUserController {
	
	private static final String SUCCESS = "success";
	// test ModelAndView
	@RequestMapping(value = "/testModelAndView", method = RequestMethod.GET)
	public ModelAndView testMV() {

		ModelAndView mv = new ModelAndView("success2");
		mv.addObject("time", new Date().toString()); // 实质上存放到request

		return mv;
	}

	// test param-Map<String, Object>
	@RequestMapping(value = "/testMap")
	public String testMap(Map<String, Object> map) {

		map.put("list", Arrays.asList("zhang", "li", "wang"));
		return "success";
	}

	// test param-org.springframework.ui.Model
	@RequestMapping(value = "/testmodel", method = RequestMethod.GET)
	public ModelAndView testMap(Model model) {

		ModelAndView mv = new ModelAndView("success-model");
		mv.addObject("time", new Date().toString() + ";model"); // 实质上存放到request
		return mv;
		
	}
	
	
	// test param-org.springframework.ui.ModelMap
	@RequestMapping(value = "/testModelMap", method = RequestMethod.GET)
	public ModelAndView testModelMap(ModelMap mm) {

		ModelAndView mv = new ModelAndView("success-mm");
		mv.addObject("time", new Date().toString() + ";mm"); // 实质上存放到request
		return mv;
		
	}
	@RequestMapping(value = "/testMultiViews", method = RequestMethod.GET)
	public ModelAndView testMultiViews(ModelMap mm) {

		ModelAndView mv = new ModelAndView("p1/p1");
		mv.addObject("time", new Date().toString() + ";MultiViews"); // 实质上存放到request
		return mv;
		
	}
	
	
	@RequestMapping(value = "/testPortViews", method = RequestMethod.GET)
	public ModelAndView testPortViews(ModelMap mm) {

		ModelAndView mv = new ModelAndView("port/index");
		mv.addObject("time", new Date().toString() + ";testPortViews"); // 实质上存放到request
		return mv;
		
	}
	
}
