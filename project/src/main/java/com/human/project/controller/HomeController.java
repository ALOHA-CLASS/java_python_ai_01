package com.human.project.controller;




import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.human.project.domain.Chart;
import com.human.project.domain.Users;
import com.human.project.mapper.ChartRepository;
import com.human.project.mapper.UserMapper;
import com.human.project.service.UserService;
import com.human.project.util.ValidationUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;	
	
	@Autowired
	private ValidationUtil validationUtil;

	@Autowired
    private ChartRepository chartRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserMapper userMapper;


	
	@GetMapping({"", "/", "/index"})
    public String getChart(Chart track, Model model
                          ,@AuthenticationPrincipal OAuth2User principal) throws IOException {
        List<Chart> trackList = chartRepository.findAll();
        model.addAttribute("trackList", trackList);
        
        // 
        if( principal != null ) {
        Map<String, Object> map = principal.getAttributes();
        log.info("map : " + map);
        log.info("map : " + map.get("properties"));
        if(map.get("properties") != null) {
			Map<String, Object> proMap = (Map<String, Object>) map.get("properties");
			Map<String, Object> accountMap = (Map<String, Object>) map.get("kakao_account");

			String profile_image = String.valueOf( proMap.get("profile_image") );
			String thumbnail_image = String.valueOf( proMap.get("thumbnail_image") );

			String email = String.valueOf( accountMap.get("email") );


			log.info("map : " + proMap);
			log.info("email : " + email);
			log.info("profile_image : " + proMap.get("profile_image"));

			model.addAttribute("email", email);
			model.addAttribute("profile_image", profile_image);
			model.addAttribute("thumbnail_image", thumbnail_image);
        }
      }
		return "index";
	}
	
	//로그인
	@GetMapping("/login")
	public String login(Model model, @CookieValue(value = "remember-id", required = false) Cookie cookie) {
		
		boolean rememberId = false;			// 아이디 저장 체크 여부
		String userId = "";
		if(cookie != null) {
			userId = cookie.getValue();
			rememberId = true;
		}
		
		model.addAttribute("userId", userId);
		model.addAttribute("rememberId", rememberId);	
		
		return "login";
	}
	
	// 회원가입 화면
	@GetMapping("/join")
	public String join(Users user) {
		log.info("회원가입 화면 ..");
		
		return "join";
	}
	
	@GetMapping("/chart")
    public String getlist() { return "chart"; }
	
		
	/**
	 * 회원가입 처리
	 * 
	 * * @Validated		: 입력값 검증 기능 활성화
	 * * BindingResult	: 요청 데이터의 바인딩 및 검증 오류 정보
	 * @param user
	 * @return
	 * @throws Exception
	 */
	
	@PostMapping("/join")
	public String joinPro(@Validated Users user, BindingResult bindingResult, HttpServletRequest request) throws Exception {
		
		// 유효성 검증 오류확인
		if(validationUtil.joinCheckError(bindingResult, user)) {
			log.info("유효성 검증 오류..");
			return "join";
		}
		
		// 회원가입 처리
		int result = userService.join(user);
		boolean isAuthentication = false;
		if(result > 0 ) {
			log.info("회원가입 성공..");
			// 바로 로그인
			isAuthentication = userService.tokenAuthentication(user, request);
		}
		else {
			log.info("회원가입 실패..");
		}
		
		// 인증(바로 로그인) 실패
		if(!isAuthentication) {
			return "redirect:/login";
		}
		
		return "redirect:/";
	}
	
    // // 아이디 찾기
	// @PostMapping("/find_id")
	// public ResponseEntity<List> findId(Users user) throws Exception {    	
	// 	Users selectedUser = userService.selectByEmail(user);
	// 	String findId = selectedUser.getUserId();
	// 	String findName = selectedUser.getName();
	// 	List<String> result = Arrays.asList(findId, findName);
	// 	return new ResponseEntity<List>(result, HttpStatus.OK);
    // }
    
  	// 비밀번호 찾기
    // @GetMapping("/find_password")
    // public String doFind123123(Users user, Model model) throws Exception {
    	
    // 	Users result = userService.selectByEmail(user);
    // 	log.info(result.getEmail());
    // 	log.info(result.getName());
    // 	log.info(result.getNickname());
    	
    //     return "result";
    // }
    
    @GetMapping("/find")
    public String doFind() {
        return "find";
    }

	// 아이디 찾기
	@PostMapping("/find_id")
	public ResponseEntity<String> findId(Users user) throws Exception {
	
		Users selectedId = userService.findId(user);
		String findId = selectedId.getUserId();
		
		if(selectedId == null) {
			return new ResponseEntity<>("fail", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(findId, HttpStatus.OK);
		}

	}
	
	// 비밀번호 찾아서 임시 비밀번호 발급
	@PostMapping("/find_password")
	public ResponseEntity<String> findPw(Users user) throws Exception {
		
		Users selectedPw = userService.findPw(user);
		UUID uuid = UUID.randomUUID();
		String uuidPw = uuid.toString().substring(0, 15);
		// log.info("비밀번호@@@@@@@@@@@@@@@@@@@@" + uuidPw);

		if(selectedPw == null) {
            return new ResponseEntity<>("fail", HttpStatus.OK);
        }
        else {
			// 비밀번호 암호화
			// newPw 암호화 - UPDATE users user-pw 
			String newPw = passwordEncoder.encode(uuidPw);
			user.setUserPw(newPw);
			userMapper.newPw(user);
            return new ResponseEntity<>(uuidPw, HttpStatus.OK);
        }

	}

	// 비밀번호 변경
	@PostMapping("/newPwUpdate")
	// public ResponseEntity<String> newPwUpdate(@Validated Users user, Authentication authentication, BindingResult bindingResult) throws Exception {
	public ResponseEntity<String> newPwUpdate(Users user, Authentication authentication) throws Exception {
		
		// 유효성 검증 오류확인
		if(!validationUtil.passwordError(user)) {
			log.info("유효성 검증 오류..");
			return new ResponseEntity<>("false", HttpStatus.OK);
		}

		String userId = authentication.getName();
		user.setUserId(userId);

		int newPw = userService.newPw(user);

		String newPwStr = user.getUserPw();
		log.info("변경 할 비밀번호 " + newPwStr);

		// // 비밀번호 암호화
		newPwStr = passwordEncoder.encode(newPwStr);
		user.setUserPw(newPwStr);
		userMapper.newPw(user);

		return new ResponseEntity<>("true", HttpStatus.OK);
	}

	// 이메일 변경
	@PostMapping("/newEmailUpdate")
	// public ResponseEntity<String> newPwUpdate(@Validated Users user, Authentication authentication, BindingResult bindingResult) throws Exception {
	public ResponseEntity<String> newEmailUpdate(Users user, Authentication authentication) throws Exception {
		
		// 유효성 검증 오류확인
		if(!validationUtil.emailError(user)) {
			log.info("유효성 검증 오류..");
			return new ResponseEntity<>("fail", HttpStatus.OK);
		}

		Users selectedEmail = userService.select(user);
		String email = user.getEmail();
		log.info("이메일 : " + email);

		if( selectedEmail != null ) {
			log.info("이메일 중복... : " + email);
			return new ResponseEntity<>("false", HttpStatus.OK);
		}

		String userId = authentication.getName();
		user.setUserId(userId);

		int newEmail = userService.newEmail(user);

		String newEmailStr = user.getEmail();

		// String userId = authentication.getName();
		// user.setUserId(userId);

		// int newEmail = userService.newEmail(user);

		// String newEmailStr = user.getEmail();
		log.info("변경 할 이메일 " + newEmailStr);

		return new ResponseEntity<>("true", HttpStatus.OK);
	}
    
    // 아이디 찾기
	@GetMapping("/main")
	public String community() {
		return "main";
	}

}