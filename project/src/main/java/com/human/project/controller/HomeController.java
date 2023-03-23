package com.human.project.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.human.project.domain.Chart;
import com.human.project.domain.Users;
import com.human.project.mapper.ChartRepository;
import com.human.project.service.UserService;
import com.human.project.util.ValidationUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ValidationUtil validationUtil;

	@Autowired
    private ChartRepository chartRepository;

	@GetMapping("/")
    public String getChart(Chart track, Model model, HttpSession ses) throws IOException {
        List<Chart> trackList = chartRepository.findAll();
        model.addAttribute("trackList", trackList);
        return "/index";
    }
	
	@GetMapping({"", "/", "/index"})
	public String index() {
		return "/index";
	}
	
	// 로그인
	@GetMapping("/login")
	public String login(Model model
					  ,@CookieValue(value = "remember-id", required = false) Cookie cookie ) {
		
		boolean rememberId = false;		// 아이디 저장 체크 여부
		String userId = "";
		
		// log.info("쿠키 : " + cookie.toString());
		
		if( cookie != null ) {
			userId = cookie.getValue();
			rememberId = true;
		}
		
		model.addAttribute("userId", userId);
		model.addAttribute("rememberId", rememberId);
		
		return "/login";
	}
	
	// 회원가입
	@GetMapping("/join")
	public String join(Users user) {
		log.info("회원가입 화면...");
		
		return "/join";
	}
	
	/**
	 * 회원가입 처리
	 * 
	 * * @Validated		: 입력값 검증 기능 활성화
	 * * BindingResult	: 요청 데이터의 바인딩 및 검증 오류 정보
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/join")
	public String joinPro(@Validated Users user, BindingResult bindingResult, HttpServletRequest request) throws Exception {
		
		// 유효성 검증 오류확인
		if( validationUtil.joinCheckError(bindingResult, user) ) {
			log.info("유효성 검증 오류...");
			return "/join";
		}
		
		// 회원가입 처리
		int result = userService.join(user);
		
		boolean isAuthentication = false;
		if( result > 0 ) {
			log.info("회원가입 성공...");
			// 바로 로그인
			isAuthentication = userService.tokenAuthentication(user, request);
		} else {
			log.info("회원가입 실패...");
		}
		
		// 인증(바로 로그인) 실패
		if( !isAuthentication ) {
			return "redirect:/login";
		}
		
		
		return "redirect:/";
	}
	
	@GetMapping("/main")
	public String community() {
		return "/main";
	}
	
	
	

}












