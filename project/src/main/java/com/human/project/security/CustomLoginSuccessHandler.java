package com.human.project.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import lombok.extern.slf4j.Slf4j;

// 로그인 성공 처리 클래스
@Slf4j
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {@Override
	
	
	public void onAuthenticationSuccess(HttpServletRequest request
									   ,HttpServletResponse response
									   ,Authentication authentication) throws ServletException, IOException {
	
		log.info("인증 처리 성공...");
		
		// 아이디 저장
		String rememberId = request.getParameter("remember-id");
		String username = request.getParameter("id");
		
		log.info("아이디 저장 여부 : " + rememberId);
		
		// 아이디 저장 체크O → 쿠키 생성
		if( rememberId != null && rememberId.equals("on") ) {
			// new Cookie(쿠키이름, 값);
			Cookie cookie = new Cookie("remember-id", username);
			cookie.setMaxAge(60*60*24);		// 유효기간 : 1일 (단위 : 초)
			cookie.setPath("/");
			response.addCookie(cookie);
		}
		
		// 아이디 저장 체크X → 쿠키 삭제
		else {
			Cookie cookie = new Cookie("remember-id", null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
	

}














