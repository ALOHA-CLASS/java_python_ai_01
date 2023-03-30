package com.human.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.human.project.domain.Option;
import com.human.project.domain.Page;
import com.human.project.domain.UserAuth;
import com.human.project.domain.Users;
import com.human.project.mapper.AdminMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminMapper adminMapper;
	
	// 회원 목록 페이징
	@Override
	public List<Users> userPage(Page page, Option option) throws Exception {
		// 전체 회원 수
		int totalCnt = adminMapper.cntWithKeyword(option);
		log.info("userCnt : " + totalCnt);
		
		// 페이징 처리
		page.setTotalCount(totalCnt);
		page.calc(page);
		
		List<Users> userList = adminMapper.userPage(page, option);
		
		// 권한 추가
		for (Users user : userList) {
			String userId = user.getUserId();
			List<UserAuth> authList = adminMapper.authListByUid(userId);
			user.setAuthList(authList);
		}
		
		return userList;
	}
	
	// 회원 선택 삭제
	@Override
	public int delete(List<String> userNoList) throws Exception {
		String noList = "";
		for (int i = 0; i < userNoList.size(); i++) {
			noList += userNoList.get(i);
			if (i+1 != userNoList.size() ) {
				noList += ", ";
			}
		}
//			log.info("noList : " +  noList );
		int result1 = adminMapper.userDelBoardUpdate(noList);
		if (result1 > 0)	log.info("회원 삭제 시 게시판 작성사 수정 성공");
		else				log.info("회원 삭제 시 게시판 작성사 수정 실패");
		int result2 = adminMapper.userDelCommentUpdate(noList);
		if (result2 > 0)	log.info("회원 삭제 시 댓글 작성사 수정 성공");
		else				log.info("회원 삭제 시 댓글 작성사 수정 실패");
		
		int result = adminMapper.deleteSelectedUser(noList);
		if (result > 0)		log.info("회원 다중 삭제 성공");
		else				log.info("회원 다중 삭제 실패");
			
		return result;
	}
	
	// 회원 권한 추가
	@Override
	public int addAuth(String userNo, String authVal) throws Exception {
		
		int result = 0;
		
		// 회원 권한 확인
		int result1 = adminMapper.authChk(userNo, authVal);
		if (result1 > 0)	log.info("중복 있음");
		// 회원 권한 추가
		else 				result = adminMapper.addAuth(userNo, authVal);
		
		return result;
	}

	// 회원 권한 삭제
	@Override
	public int delAuth(String userNo, String authVal) throws Exception {
		
		// 회원 권한 삭제
		int result = adminMapper.delAuth(userNo, authVal);
		
		return result;
	}
	
	// enable 수정
	@Override
	public int modEna(String userNo, int zeroOne) throws Exception {
		int result = adminMapper.modEna(userNo, zeroOne);
		return result;
	}

	// 닉네임 변경
	@Override
	public int modNick(List<String> userNoList) throws Exception {
		String noList = "";
		for (int i = 0; i < userNoList.size(); i++) {
			noList += userNoList.get(i);
			if (i+1 != userNoList.size() ) {
				noList += ", ";
			}
		}
		int result = adminMapper.modNick(noList);
		return result;
	}

	// 유저 권한 리스트
	@Override
	public List<UserAuth> authListByUid(String userId) throws Exception {
		return adminMapper.authListByUid(userId);
	}

	// 게시글 선택 삭제
	@Override
	public int deleteSelectedBoard(List<String> boardNoList) throws Exception {
		String noList = "";
		for (int i = 0; i < boardNoList.size(); i++) {
			noList += boardNoList.get(i);
			if (i+1 != boardNoList.size() ) {
				noList += ", ";
			}
		}
		int result = adminMapper.deleteSelectedBoard(noList);
		return result;
	}
}