package com.human.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.human.project.domain.UserAuth;
import com.human.project.domain.Users;

@Mapper
public interface UserMapper {
	
	// 회원가입
	public int join(Users user) throws Exception;
	
	// 권한등록
	public int insertAuth(UserAuth userAuth) throws Exception;
	
	// 회원조회
	public Users select(Users user) throws Exception;
	

	// 아이디 찾기

	

	// 회원 목록
	public List<Users> list() throws Exception;

}