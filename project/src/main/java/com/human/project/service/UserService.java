package com.human.project.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.human.project.domain.UserAuth;
import com.human.project.domain.Users;

public interface UserService {

	// 회원가입
	public int join(Users user) throws Exception;
	
	// 권한등록
	public int insertAuth(UserAuth userAuth) throws Exception;
	
	// 회원조회
	public Users select(Users user) throws Exception;
	
	// 토큰인증 (바로 로그인)
	public boolean tokenAuthentication(Users user, HttpServletRequest request) throws Exception;
	
	// 회원 목록
	public List<Users> list() throws Exception;
}
