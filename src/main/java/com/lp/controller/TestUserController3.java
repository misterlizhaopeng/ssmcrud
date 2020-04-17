package com.lp.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes(value = { "list" }, types = { String.class })
@RequestMapping("springmvc")
@Controller
public class TestUserController3 {
	private static final String SUCCESS = "success";
	private static final String sessionAttribute="session-attribute";

	@RequestMapping(value = "/testSessionAttributes")
	public String testSessionAttributes(Map<String, Object> map) {
		map.put("list", Arrays.asList("a", "b", "c"));
		map.put("nameAge", "zhangsan");
//		System.out.println();
		return sessionAttribute;
	}
	
	
	@ModelAttribute
    public void getPerson(@RequestParam(value = "id",defaultValue="0") Integer id, Map<String, Object> map) {
        if (id != null) {
            map.put("person", "person model");
            System.out.println(map);	
        }
    }

}