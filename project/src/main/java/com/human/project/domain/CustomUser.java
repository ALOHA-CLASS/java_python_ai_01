package com.human.project.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

public class CustomUser extends DefaultOAuth2User {
	
	
	private static final long serialVersionUID = 1L;
	
	private String username;

	public CustomUser(String username, Collection<? extends GrantedAuthority> authorities, OAuthAttributes attributes) {
		super(authorities, attributes.getAttributes(), attributes.getNameAttributeKey());
		
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	

}
