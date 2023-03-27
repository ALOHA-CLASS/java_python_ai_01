package com.human.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.human.project.domain.Users;
import com.human.project.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
	private UserService userService;

    
    @GetMapping("/list")
	public String information(Model model, Users user, Authentication authentication) throws Exception{
		String userId = authentication.getName();
		user.setUserId(userId);
		Users selectedUser = userService.select(user);
		model.addAttribute("user", selectedUser);
		return "/profile/list";
	}

    @GetMapping("/update")
	public String update(Model model, Users user, Authentication authentication) throws Exception {
        String userId = authentication.getName();
		user.setUserId(userId);
		Users selectedUser = userService.select(user);
		model.addAttribute("user", selectedUser);
		return "/profile/update";
	}
	
	// 게시글 수정 - 처리
	@PostMapping("/update")
	public String updatePro(Users user) throws Exception {

		int result = userService.update(user);

		if (result > 0) log.info("회원정보 수정 성공");
		else			log.info("회원정보 수정 실패");

		return "redirect:/profile/list";
	}
	
}
