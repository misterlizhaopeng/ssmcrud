package com.lp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tst")
public class TestUserController2 {
	private static final String SUCCESS = "success";
	@RequestMapping(value = "/testMap")
	public String testMap(Map<String, Object> map,HttpServletRequest httpServletRequest) {

//		map.put("list", Arrays.asList("zhang", "li", "wang"));
		
		Object attribute = httpServletRequest.getSession().getAttribute("list");
		System.out.println(attribute);
		return "success";
	}
 
}
