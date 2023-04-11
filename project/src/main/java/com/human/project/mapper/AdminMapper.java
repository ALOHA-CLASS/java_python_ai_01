package com.human.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.human.project.domain.Option;
import com.human.project.domain.Page;
import com.human.project.domain.UserAuth;
import com.human.project.domain.Users;


@Mapper
public interface AdminMapper {
		
	// [페이지+검색] 회원 목록
	public List<Users> userPage(@Param("page") Page page,  @Param("option") Option option) throws Exception;
	
	// 회원 검색 - 회원 수
	public int cntWithKeyword(@Param("option") Option option) throws Exception;
	
	// 선택 회원 삭제
	public int deleteSelectedUser(@Param("noList") String noList) throws Exception;
	
	// 회원 삭제시 게시글 작성자 수정
	public int userDelBoardUpdate(@Param("noList") String noList) throws Exception;
	
	// 회원 삭제시 게시글 작성자 수정
	public int userDelCommentUpdate(@Param("noList") String noList) throws Exception;
	
	// 회원 권환 확인
	public int authChk(@Param("userNo") String userNo, @Param("authVal") String authVal) throws Exception;
	
	// 회원 권한 추가
	public int addAuth(@Param("userNo") String userNo, @Param("authVal") String authVal) throws Exception;
	
	// 회원 권한 삭제
	public int delAuth(@Param("userNo") String userNo, @Param("authVal") String authVal) throws Exception;
	
	// 회원 Enable 0 수정
	public int modEna(@Param("userNo") String userNo, @Param("zeroOne") int zeroOne) throws Exception;
	
	// 회원 닉네임 변경
	public int modNick(@Param("noList") String noList) throws Exception;
	
	// 회원 권한 조회 (아이디로)
	public List<UserAuth> authListByUid(String userId) throws Exception;
	
	// 게시글 선택 삭제
	public int deleteSelectedBoard(@Param("noList") String noList) throws Exception;
	
	
}
