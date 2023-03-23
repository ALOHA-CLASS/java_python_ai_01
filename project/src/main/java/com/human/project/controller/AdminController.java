package com.human.project.controller;

//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

	@GetMapping({"", "/", "/index"})
	public String index() {
		log.info("사용자 페이지..");
		
		return "/admin/index";
		
	}
}
