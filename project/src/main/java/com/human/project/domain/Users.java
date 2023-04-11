package com.human.project.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.human.project.util.Unique;

import lombok.Data;

@Data
public class Users {
	
	private int userNo;
	
	@NotBlank(message = "*반드시 입력해야합니다.")
	private String userId;
	
	@NotBlank(message = "*반드시 입력해야합니다.")
	@Size(min = 8, max = 20, message = "*8글자 이상 20자 이하로 입력해야합니다.")
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,20}", message = "영문자, 숫자, 특수문자를 각각 하나 이상 포함해야합니다.")
	private String userPw;
	
	@NotBlank(message = "*반드시 입력해야합니다.")
	private String userPwChk;
	
	@NotBlank(message = "*반드시 입력해야합니다.")
	private String nickname;

	@NotBlank(message = "*반드시 입력해야합니다.")
	private String name;
	
	@NotBlank(message = "*반드시 입력해야 합니다.")
	@Email(message = "*이메일 형식으로 입력해야 합니다.")
	@Unique(domainClass = Users.class, email = "email", message="이메일 중벅")
	private String email;
	
	private boolean enabled;
	private LocalDateTime regDate;
	private LocalDateTime updDate;
	
	//권한 목록
	private List<UserAuth> authList;
}
