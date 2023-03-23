package com.human.project.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
			String userId = user.getUserId();
			userAuth.setUserId(userId);
			userAuth.setAuth("ROLE_USER");
			userMapper.insertAuth(userAuth);
		}
		
		return result;
	}

	@Override
	public int insertAuth(UserAuth userAuth) throws Exception {
		int result = userMapper.insertAuth(userAuth);
		return result;
	}

	@Override
	public Users select(Users user) throws Exception {
		Users selectedUser = userMapper.select(user);
		return selectedUser;
	}

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


	@Override
	public List<Users> list() throws Exception {
		List<Users> usersList = userMapper.list();
		return usersList;
	}
	


}


















