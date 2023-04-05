package com.human.project.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.human.project.domain.UserAuth;
import com.human.project.domain.UserSocial;
import com.human.project.domain.Users;

public interface UserService {
	
	// 회원가입
	public int join(Users user) throws Exception;
		
	// 권한등록
	public int insertAuth(UserAuth userAuth) throws Exception;
	
	// social 회원가입
	public int insertSocial(UserSocial userSocial) throws Exception;
	
	// 회원조회
	public Users select(Users user) throws Exception;
	public Users selectByEmail(Users user) throws Exception;
	
	// 토큰인증 (바로 로그인)
	public boolean tokenAuthentication(Users user, HttpServletRequest request) throws Exception;

	public int joinSocial(Users user, HttpServletRequest request) throws Exception;

	// 회원 수정
	public int update(Users user) throws Exception;
	
	// 회원 삭제
	public int delete(Users user) throws Exception;

	// 아이디 찾기
	public Users findId(Users user) throws Exception;

	// 비밀번호 찾기
	public Users findPw(Users user) throws Exception;

	// 임시 비밀번호 발급 & 비밀번호 변경
	public int newPw(Users user) throws Exception;

	// 모든 쿠키 삭제
	public boolean deleteCookies(HttpServletRequest request, HttpServletResponse response) throws Exception;

	// 이메일 변경
	public int newEmail(Users user) throws Exception;
}
