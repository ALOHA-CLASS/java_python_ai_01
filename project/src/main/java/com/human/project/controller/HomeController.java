package com.human.project.controller;



import java.util.Map;

import java.io.IOException;
import java.util.List;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
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


	
	@GetMapping({"", "/", "/index"})
    public String getChart(Chart track, Model model
                          ,@AuthenticationPrincipal OAuth2User principal) throws IOException {
        List<Chart> trackList = chartRepository.findAll();
        model.addAttribute("trackList", trackList);
        
        // 
        if( principal != null ) {
        Map<String, Object> map = principal.getAttributes();
        log.info("map : " + map);
        log.info("map : " + map.get("properties"));

        Map<String, Object> proMap = (Map<String, Object>) map.get("properties");
        Map<String, Object> accountMap = (Map<String, Object>) map.get("kakao_account");

        String profile_image = String.valueOf( proMap.get("profile_image") );
        String thumbnail_image = String.valueOf( proMap.get("thumbnail_image") );

        String email = String.valueOf( accountMap.get("email") );


        log.info("map : " + proMap);
        log.info("email : " + email);
        log.info("profile_image : " + proMap.get("profile_image"));

        model.addAttribute("email", email);
        model.addAttribute("profile_image", profile_image);
        model.addAttribute("thumbnail_image", thumbnail_image);
      }
		return "/index";
	}
	
	//로그인
	@GetMapping("/login")
	public String login(Model model, @CookieValue(value = "remember-id", required = false) Cookie cookie) {
		
		boolean rememberId = false;			// 아이디 저장 체크 여부
		String userId = "";
		if(cookie != null) {
			userId = cookie.getValue();
			rememberId = true;
		}
		
		model.addAttribute("userId", userId);
		model.addAttribute("rememberId", rememberId);	
		
		return "/login";
	}
	
	// 회원가입 화면
	@GetMapping("/join")
	public String join(Users user) {
		log.info("회원가입 화면 ..");
		
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
		if(validationUtil.joinCheckError(bindingResult, user)) {
			log.info("유효성 검증 오류..");
			return "/join";
		}
		
		// 회원가입 처리
		int result = userService.join(user);
		
		boolean isAuthentication = false;
		if(result > 0 ) {
			log.info("회원가입 성공..");
			// 바로 로그인
			userService.tokenAuthentication(user, request);
		}
		else {
			log.info("회원가입 실패..");
		}
		
		// 인증(바로 로그인) 실패
		if(!isAuthentication) {
			return "redirect:/login";
		}
		
		return "redirect:/";
	}
	
    //아이디&비밀번호 찾기
    @GetMapping("/find")
    public String doFind() {
        return "/find";
    }
    
    // 아이디 찾기
	@GetMapping("/main")
	public String community() {
		return "/main";
	}
	
	
	



}