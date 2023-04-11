package com.human.project.controller;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.human.project.domain.OAuthToken;
import com.human.project.domain.Users;
import com.human.project.service.KakaoLoginService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class KakaoController {

    @Autowired
    private KakaoLoginService kakaoLoginService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/authorize/kakao")
    public ResponseEntity<String> getAuthorizationCode(String code) throws Exception {

        log.info("인가코드 : " + code);

        // 인가코드 받아오기 실패!
        if( code == null ) {
            return new ResponseEntity<>("Authorize Code FAIL", HttpStatus.OK);
        }

        // 인증코드 요청하기
        OAuthToken oAuthToken = kakaoLoginService.getAccessToken(code);
        String accessToken = oAuthToken.getAccess_token();
        
        // 인증토큰 받아오기 실패
        if( oAuthToken == null ) {
            return new ResponseEntity<>("Authentication Token FAIL", HttpStatus.OK);
        }
        
        log.info("액세스 토큰 바디 : " + oAuthToken);
        log.info("액세스 토큰 : " + oAuthToken.getAccess_token());


        Users user = kakaoLoginService.getUserInfo(accessToken);
        log.info("카카오 로그인 인증 후 - 가져온 사용자 정보");
        log.info("user : " + user);

        // 회원가입 여부 확인 및 회원가입 처리
        

        // 로그인 처리
        String userId = user.getUserId();
        // UserSocial 에서 암호화 안 된 비번을 가져오거나, 소셜로그인 공통비번을 사용할 것
        String userPw = "kakao123456";

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, userPw);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        
        return new ResponseEntity<>(user.toString(), HttpStatus.OK);
    }

    
    
}
