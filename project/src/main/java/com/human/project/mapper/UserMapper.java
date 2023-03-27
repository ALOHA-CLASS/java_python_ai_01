package com.human.project.mapper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Mapper;

import com.human.project.domain.UserAuth;
import com.human.project.domain.UserSocial;
import com.human.project.domain.Users;

@Mapper
public interface UserMapper {
	
	// 회원가입
	public int join(Users user) throws Exception;
	
	// 소셜 회원가입
	public int joinSocial(Users user, HttpServletRequest request) throws Exception;
	
	// 권한등록
	public int insertAuth(UserAuth userAuth) throws Exception;
	
	// kakao 회원가입
	public int insertSocial(UserSocial userSocial) throws Exception;
	
	// 아이디 조회
	public Users select(Users user) throws Exception;
	public Users selectByEmail(Users user) throws Exception;

	public UserSocial selectByEmail2(String email) throws Exception;

	public UserSocial selectSocial(UserSocial userSocial) throws Exception;
	

	// 아이디 찾기

	

	// 회원 목록
	public List<Users> list() throws Exception;

	// 회원 수정
	public int update(Users user) throws Exception;
	
	// 회원 삭제
	public int delete(Users user) throws Exception;
}
