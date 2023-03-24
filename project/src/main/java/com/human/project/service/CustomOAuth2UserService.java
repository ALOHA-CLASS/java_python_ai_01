package com.human.project.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.human.project.domain.CustomUser;
import com.human.project.domain.OAuthAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
	
		log.info("loadUser() ...");
	
		OAuth2UserService delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);
		
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		String userNameAttributeName= userRequest.getClientRegistration().getProviderDetails()
												 .getUserInfoEndpoint().getUserNameAttributeName();
		
		log.info("loadUser registrationId = " + registrationId);
		log.info("loadUser userNameAttributeName = " + userNameAttributeName);
		
		OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
		
		log.info("attributes - " + attributes.getAttributes());
		
		
		String nameAttributeKey = attributes.getNameAttributeKey();
		String name = attributes.getName();
		String nickname = attributes.getNickname();
		String email = attributes.getEmail();
		String picture = attributes.getPicture();
		String id = attributes.getId();
		String socialType = "";
		
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
		log.info("loaduser - id =  " + id);
		log.info("loaduser - socialType =  " + socialType);
		log.info("loaduser - name =  " + name);
		log.info("loaduser - email =  " + email);
		log.info("loaduser - picture =  " + picture);
		
		if( name == null ) name = "";
		if( email == null ) email = "";
		
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
		authorities.add(authority);
		
		return new CustomUser(name, authorities, attributes);
	}
	
	
}