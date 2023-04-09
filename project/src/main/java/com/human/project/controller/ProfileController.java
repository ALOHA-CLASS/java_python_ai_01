package com.human.project.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
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

    
    @GetMapping("/my")
	public String information(Model model, Users user, Authentication authentication) throws Exception{
		String userId = authentication.getName();
		user.setUserId(userId);
		Users selectedUser = userService.select(user);
		model.addAttribute("user", selectedUser);
		return "profile/my";
	}

    @GetMapping("/update")
	public String update(@AuthenticationPrincipal OAuth2User principal, Model model, Users user, Authentication authentication) throws Exception {
        String userId = authentication.getName();
		user.setUserId(userId);
		Users selectedUser = userService.select(user);
		model.addAttribute("user", selectedUser);

		if( principal != null ) {
			Map<String, Object> map = principal.getAttributes();
			if(map.get("kakao_account") != null) {
			Map<String, Object> accountMap = (Map<String, Object>) map.get("kakao_account");
			String email = String.valueOf( accountMap.get("email") );
			model.addAttribute("social_email", email);
			log.info(email);
			}
			if(map.get("response") != null) {
				Map<String, Object> accountMap = (Map<String, Object>) map.get("response");
				String email = String.valueOf( accountMap.get("email") );
				user.setEmail(email);	
			}
		}
		return "profile/update";
	}
	
	@PostMapping("/update")
	public String updatePro(@AuthenticationPrincipal OAuth2User principal, Users user, Authentication authentication) throws Exception {
		String userId = authentication.getName();
		user.setUserId(userId);
		
		if(principal != null) {
			Map<String, Object> map = principal.getAttributes();
			log.info("map.get(\"kakao_account\")"+map.get("kakao_account"));
			log.info("map.get(\"response\")"+map.get("response"));
			if(map.get("kakao_account") != null) {
			
			Map<String, Object> accountMap = (Map<String, Object>) map.get("kakao_account");
			String email = String.valueOf( accountMap.get("email") );
			user.setEmail(email);	
			}
			if(map.get("response") != null) {
			Map<String, Object> accountMap = (Map<String, Object>) map.get("response");
			String email = String.valueOf( accountMap.get("email") );
			user.setEmail(email);	
			}
		}
		int result = userService.update(user);
		if (result > 0) log.info("회원정보 수정 성공");
		else			log.info("회원정보 수정 실패");

		return "redirect:/profile/my";
	}

	@PostMapping("/delete")
	public String delete(Users user, Authentication authentication, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		String userId = authentication.getName();
		user.setUserId(userId);
		int result = userService.delete(user);
		if (result > 0){
			log.info("회원정보 삭제 성공");

			// 쿠키 삭제
			userService.deleteCookies(request, response);

			// 세션 무효화(로그아웃)
			session.invalidate();
		} 
		else {
			log.info("회원정보 삭제 실패");
		}

		return "redirect:/";
	}
	
}
