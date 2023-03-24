package com.human.project.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.human.project.domain.UserAuth;
import com.human.project.domain.UserSocial;
import com.human.project.domain.Users;

@Mapper
public interface UserMapper {
	
	// 회원가입
	public int join(Users user) throws Exception;
	
	// 권한등록
	public int insertAuth(UserAuth userAuth) throws Exception;
	
	// kakao 회원가입
	public int insertSocial(UserSocial userSocial) throws Exception;
	
	// 아이디 조회
	public Users select(Users user) throws Exception;
	
}
