package com.human.project.service;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import com.human.project.domain.UserAuth;
import com.human.project.domain.UserSocial;
import com.human.project.domain.Users;
import com.human.project.mapper.UserMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;	// 인증 관리자

	// 회원가입
	@Override
	public int join(Users user) throws Exception {
		// 비밀번호 암호화
		String userPw = user.getUserPw();
		userPw = passwordEncoder.encode(userPw);
		user.setUserPw(userPw);
		
		int result = userMapper.join(user);
		
		// 기본 사용자 권한 등록
		if( result > 0 ) {
			UserAuth userAuth = new UserAuth();
			UserSocial userSocial = new UserSocial();
			String userId = user.getUserId();
			userAuth.setUserId(userId);
			userAuth.setAuth("ROLE_USER");
			userMapper.insertAuth(userAuth);
			userSocial.setUserId(userId);
			userSocial.setSocialType("NORMAL");
//			userMapper.insertSocial(userSocial);
		}
		
		return result;
	}
	
	@Override
	public int joinSocial(Users user, HttpServletRequest request) throws Exception {
		// 비밀번호 암호화
		UUID password = UUID.randomUUID();
		String userPw = passwordEncoder.encode(password.toString());
		user.setUserPw(userPw);
		
		UUID id = UUID.randomUUID();
		String userId = passwordEncoder.encode(id.toString());
		user.setUserId(userId); 
		
		UUID nickname = UUID.randomUUID();
		String userNick = passwordEncoder.encode(nickname.toString());
		user.setUserId(userNick);
		
		String socialType = "KAKAO";
		
		int result = userMapper.join(user);
		
		// 기본 사용자 권한 등록
		if( result > 0 ) {
			UserAuth userAuth = new UserAuth();
			UserSocial userSocial = new UserSocial();
			userAuth.setUserId(userId);
			userAuth.setAuth("ROLE_USER");
			userMapper.insertAuth(userAuth);
			userSocial.setUserId(userId);
			userSocial.setSocialType(socialType);
			userMapper.insertSocial(userSocial);
		}
		
		return result;
	}

	// 권한등록
	@Override
	public int insertAuth(UserAuth userAuth) throws Exception {
		int result = userMapper.insertAuth(userAuth);
		return result;
	}

	// social 회원가입
	@Override
	public int insertSocial(UserSocial userSocial) throws Exception {
		int result = userMapper.insertSocial(userSocial);
		return result;
	}

	// 회원조회
	@Override
	public Users select(Users user) throws Exception {
		Users selectedUser = userMapper.select(user);
		return selectedUser;
	}

	// 회원조회
	@Override
	public Users selectByEmail(Users user) throws Exception {
		Users selectedUser = userMapper.selectByEmail(user);
		return selectedUser;
	}

	// 토큰인증 (바로 로그인)
	@Override
	public boolean tokenAuthentication(Users user, HttpServletRequest request) throws Exception {
		
		String username = user.getUserId();
		String password = user.getUserPwChk();	// userPw 는 암호화 되기 때문에, userPwChk 를 사용
		
		// 아이디, 패스워드로 인증토큰 생성
		UsernamePasswordAuthenticationToken token 
			= new UsernamePasswordAuthenticationToken(username, password);
		
		// 토큰에 요청정보 등록
		token.setDetails( new WebAuthenticationDetails(request) );
		
		// 토큰을 이용하여 인증(로그인)
		Authentication authentication = authenticationManager.authenticate(token);
		
		// 인증 성공 여부
		boolean isAuthenticated = authentication.isAuthenticated();
		
		// 인증 실패
		if( !isAuthenticated ) {
			log.info("인증 실패...");
			return false;
		}
		
		// 인증 성공
		User authUser = (User) authentication.getPrincipal();
		log.info("인증된 사용자 아이디 : " + authUser.getUsername());
		
		// 시큐리티 컨텍스트에 인증된 객체 등록
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return true;
	}
	

	// 회원 수정
	@Override
	public int update(Users user) throws Exception {
		int result = userMapper.update(user);
		return result;
	}

	// 회원 삭제
	@Override
	public int delete(Users user) throws Exception {
		int result = userMapper.delete(user);
		return result;
	}

	// 모든 쿠키 삭제
	@Override
	public boolean deleteCookies(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Cookie[] cookies = request.getCookies();
		int count = 0;
		for (Cookie cookie : cookies) {
			String cookieName = cookie.getName();
			String cookieValue = cookie.getValue();
			Cookie deletedCookie = new Cookie(cookieName, null);
			deletedCookie.setMaxAge(0);
			response.addCookie(deletedCookie);
			count++;
		}
		if( count > 0 ) return true;

		return false;
	}
	
	// 아이디 찾기
	@Override
	public Users findId(Users user) throws Exception {
		Users findId = userMapper.findId(user);
		return findId;
	}

	// 비밀번호 찾기
	@Override
	public Users findPw(Users user) throws Exception {
		Users findPw = userMapper.findPw(user);
		return findPw;
	}
	// 임시 비밀번호 발급 & 비밀번호 변경
	@Override
	public int newPw(Users user) throws Exception {
		int result = userMapper.newPw(user);
		return result;
	}
	// 이메일 변경
	@Override
	public int newEmail(Users user) throws Exception {
		int result = userMapper.newEmail(user);
		return result;
	}
}

















