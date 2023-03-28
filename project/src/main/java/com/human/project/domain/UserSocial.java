package com.human.project.domain;

import lombok.Data;

@Data
public class UserSocial {
	
	private int socialNo;
	private String userId;
	private String socialType;
	private String accessToken;
}
