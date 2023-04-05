package com.human.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.human.project.domain.Users;
import com.human.project.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	// 사용자 페이지
	@GetMapping({"", "/", "/index"})
	public String index() {
		log.info("사용자 페이지...");
		
		return "user/index";
	}
	
	// ID 중복확인
	@ResponseBody
	@PostMapping("/check/id")
	public ResponseEntity<Boolean> checkUserId(Users user) throws Exception {
		
		Users selectedUser = userService.select(user);
		String userId = user.getUserId();
		
		// ID 중복
		if( selectedUser != null ) {
			log.info("ID 중복... : " + userId);
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}
		
		// ID 중복 아님(사용가능)
		log.info("ID 사용가능 : " + userId);
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
	// 닉네임 중복확인
	@ResponseBody
	@PostMapping("/check/nickname")
	public ResponseEntity<Boolean> checkUserNickname(Users user) throws Exception {
		
		Users selectedNickname = userService.select(user);
		String nickname = user.getNickname();
		log.info("닉네임" + nickname);
		log.info("selected" + selectedNickname);
		
		// 닉네임 중복 (사용불가)
		if( selectedNickname != null ) {
			log.info("닉네임 중복... : " + nickname);
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}
		
		// 닉네임 중복 아님(사용가능)
		log.info("닉네임 사용가능 : " + nickname);
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	// 이메일 중복확인
	@ResponseBody
	@PostMapping("/check/email")
	public ResponseEntity<Boolean> checkUserEmail(Users user) throws Exception {
			
		Users selectedEmail = userService.select(user);
		String email = user.getEmail();
		log.info("이메일 : " + email);
		log.info("selected" + selectedEmail);
			
		// 이메일 중복 (사용불가)
		if( selectedEmail != null ) {
			log.info("이메일 중복... : " + email);
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}
			
		// 이메일 중복 아님(사용가능)
		log.info("이메일 사용가능 : " + email);
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}

	
}

















