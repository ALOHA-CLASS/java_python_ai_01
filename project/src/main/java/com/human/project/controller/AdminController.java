package com.human.project.controller;

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
		log.info("관리자 페이지...");
		
		return "/admin/index";
	}

	@GetMapping("/test")
	public String tset() {
		log.info("테스트... (삭제 예정)");

		return "/admin/test";
	}

}
