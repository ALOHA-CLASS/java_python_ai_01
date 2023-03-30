package com.human.project.service;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.human.project.domain.CustomUser;
import com.human.project.domain.OAuthAttributes;
import com.human.project.domain.UserAuth;
import com.human.project.domain.UserSocial;
import com.human.project.domain.Users;
import com.human.project.mapper.UserMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
	

	@Autowired
	private UserMapper userMapper;
	
//	@Autowired
//  private RestTemplate restTemplate;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
	
		log.info("loadUser() ...");
	
		OAuth2UserService delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);
		
//		RestTemplate restTemplate = new RestTemplate();
		
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		String userNameAttributeName= userRequest.getClientRegistration().getProviderDetails()
												 .getUserInfoEndpoint().getUserNameAttributeName();
		
		log.info("loadUser registrationId = " + registrationId);
		log.info("loadUser userNameAttributeName = " + userNameAttributeName);
		
		OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
		
		log.info("attributes - " + attributes.getAttributes());
		
		
		String nameAttributeKey = attributes.getNameAttributeKey();
		String name = attributes.getName();
		// String nickname = attributes.getNickname();
		String email = attributes.getEmail();
		String picture = attributes.getPicture();
		String userId = attributes.getId();
		String socialType = "";
		String accessToken = attributes.getAccessToken();
		
		if("naver".equals(registrationId)) {
        	socialType = "naver";
        }
		else if("kakao".equals(registrationId)) {
			socialType = "kakao";
		}
        else {
        	socialType = "google";
        }

		log.info("loaduser - nameAttributeKey =  " + nameAttributeKey);
		log.info("loaduser - id =  " + userId);
		log.info("loaduser - socialType =  " + socialType);
		log.info("loaduser - name =  " + name);
		log.info("loaduser - email =  " + email);
		log.info("loaduser - picture =  " + picture);
		log.info("loaduser - accesstoken =  " + accessToken);
		
		if( name == null ) name = "";
		if( email == null ) email = "";
		
		Users user = new Users();	
		UserAuth userAuth = new UserAuth();
		UserSocial userSocial = new UserSocial();
				
		UUID password = UUID.randomUUID();
		String userPw = passwordEncoder.encode(password.toString());
		
//		UUID userid = UUID.randomUUID();
//		String userId = userid.toString();
		
		UUID kakaoNickname = UUID.randomUUID();
		String nickName = kakaoNickname.toString().substring(0, 12);
				
		user.setUserId(userId); 
		user.setUserPw(userPw);
		user.setNickname(nickName);
		user.setName(name);
		user.setEmail(email);
		userAuth.setUserId(userId);
		userAuth.setAuth("ROLE_USER");
		userSocial.setSocialType(socialType);
		userSocial.setAccessToken(accessToken);
		
		Users result1 = null;
		UserSocial result2 = null;
		
		try {
			result1 = userMapper.selectByEmail(user);			
//			result2 = userMapper.selectByEmail2(email);			
			log.info("result1: " + result1);
			log.info("result2: " + result2);
		} catch (Exception e) {
			log.info("selectbyemail 실패");
			e.printStackTrace();
		}
		
		
		if( result1 == null ) {
			
			try {
				userSocial.setUserId(userId);
				userMapper.join(user);
				userMapper.insertAuth(userAuth);
				userMapper.insertSocial(userSocial);
				log.info("join, insertAuth, insertSocial 성공");
				log.info("user: " + user);
				log.info("user: " + userAuth);
				log.info("user: " + userSocial);
			} catch (Exception e) {
				log.info("join, insertAuth, insertSocial 실패");
				e.printStackTrace();
			}
		} else {
			// 일반 계정 O, 카카오 로그인 시도
			try {
				userId = userMapper.selectByEmail(user).getUserId();
				userSocial.setUserId(userId);
				name = userId;
				if(userMapper.selectSocial(userSocial)==null) {
					userMapper.insertSocial(userSocial);
					log.info("insertSocial 성공");
				}
			} catch (Exception e) {
				log.info("insertSocial 실패");
				e.printStackTrace();
			}
			
		}
		
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
		authorities.add(authority);
		return new CustomUser(name, authorities, attributes);
	}
	
	
}







