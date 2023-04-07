package com.human.project.service;

import java.util.List;

import com.human.project.domain.Option;
import com.human.project.domain.Page;
import com.human.project.domain.UserAuth;
import com.human.project.domain.Users;


public interface AdminService {
	
	// [페이징+검색] 회원 목록
	public List<Users> userPage(Page page, Option option) throws Exception;
	
	// 회원 선택 삭제
	public int delete(List<String> userNoList) throws Exception;
	
	// 회원 Enable 0 수정
	public int modEna(String userNo, int zeroOne) throws Exception;
	
	// 회원 권한 추가
	public int addAuth(String userNo, String authVal) throws Exception;
	
	// 회원 권한 삭제
	public int delAuth(String userNo, String authVal) throws Exception;
	
	// 회원 닉네임 변경
	public int modNick(List<String> userNoList) throws Exception;
	
	// 회원 권한 조회 (아이디로)
	public List<UserAuth> authListByUid(String userId) throws Exception;
	
	// 게시글 선택 삭제
	public int deleteSelectedBoard(List<String> boardNoList) throws Exception;
}