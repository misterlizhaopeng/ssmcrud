package com.lp.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestExceptionController {
	private static final String SUCCESS = "success";
	private static final String sessionAttribute = "session-attribute";

	@RequestMapping(value = "/testDefaultHandlerExceptionResolver", method = RequestMethod.POST) // 不支持GET请求
	public String testDefaultHandlerExceptionResolver() {
		System.out.println("testDefaultHandlerExceptionResolver...");
		return "success";
	}

	
	@RequestMapping("/testSimpleMappingExceptionResolver")
	public String testSimpleMappingExceptionResolver(@RequestParam("i") int i) {
		System.out.println("testSimpleMappingExceptionResolver...");
		String[] s = new String[10];
		System.out.println(s[i]);
		return "success";
	}

}