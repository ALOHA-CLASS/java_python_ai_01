package com.human.project.util;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.human.project.domain.Users;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ValidationUtil {
	
	/**
	 * 유효성 오류 모든 로그 출력
	 * @param result
	 */
	public void allLog(BindingResult result) {
		if (result.hasErrors()) {
			List<ObjectError> allErrors = result.getAllErrors();
			List<ObjectError> globalErrors = result.getGlobalErrors();
			List<FieldError> fieldErrors = result.getFieldErrors();

			log.info("allErrors.size() = " + allErrors.size());
			log.info("globalErrors.size() = " + globalErrors.size());
			log.info("fieldErrors.size() = " + fieldErrors.size());

			for (int i = 0; i < allErrors.size(); i++) {
				ObjectError objectError = allErrors.get(i);
				log.info("allError = " + objectError);
			}
			
			for (int i = 0; i < globalErrors.size(); i++) {
				ObjectError objectError = globalErrors.get(i);
				log.info("globalError = " + objectError);
			}

			for (int i = 0; i < fieldErrors.size(); i++) {
				FieldError fieldError = fieldErrors.get(i);

				log.info("fieldError = " + fieldError);
				log.info("fieldError.getDefaultMessage() = " + fieldError.getDefaultMessage());
			}
		
		}
	}
	
	/**
	 * 비밀번호, 비밀번호 확인 일치 여부
	 * @param user
	 * @return
	 */
	public boolean checkPassword(Users user) {
		
		String userPw = user.getUserPw();
		String userPwChk = user.getUserPwChk();
		if(userPw.equals(userPwChk)) {
			return false;	// 일치
		}
		return true;		// 불일치
	}
	
	/**
	 * 회원가입 유효성 검증
	 * @param result
	 * @param user
	 * @return
	 */
	public boolean joinCheckError(BindingResult result, Users user) {
		boolean passwordCheck = checkPassword(user);
		if(passwordCheck) {
			FieldError fieldError 
				= new FieldError("users", "userPwChk", "비밀번호 확인이 일치하지 않습니다.");
			result.addError(fieldError);
		}
		
		if( result.hasErrors() ) {
			allLog(result);
			return true;
		}
		return false;
	}
	
	// 비밀번호 변경 시 유효성 & 정규식 검사
	// public boolean passwordError(BindingResult result, Users user) {
	public boolean passwordError(Users user) {
		// String pattern = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,20}$";
		// String userId = user.getUserId();
		String newPw = user.getUserPw();
		// String userPwChk = user.getUserPw();
		// String nickname = user.getNickname();
		// String name	= user.getName();
		// String email = user.getEmail();

		// 유효성 검사를 수행할 필드를 정의
		// String fieldName = "userPw";
		boolean result = true;
		log.info("newPw : " + newPw);
		log.info("newPw 글자수 : " + newPw.length());
		
		if (newPw == null || newPw.trim().isEmpty()) {
			// result.rejectValue(fieldName, "error.user", "*반드시 입력해야합니다.");
			log.info("비밀번호 유효성 검사 1차 오류");
			result = false;
            return false;
        }
		// 새 비밀번호가 8글자 미만, 20글자 초과인 경우 유효성 검사에 실패합니다.
		if (newPw.length() < 8 || newPw.length() > 20) {
			// result.rejectValue(fieldName, "error.user", "*8글자 이상 20자 이하로 입력해야합니다.");
			log.info("비밀번호 유효성 검사 2차 오류");
			result = false;
			return false;
		}
		
		// 새 비밀번호가 숫자, 영문자, 특수문자 중 하나 이상이 빠져있거나 공백이 있다면 유효성 검사에 실패합니다.
		if (!newPw.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$")) {
			// result.rejectValue(fieldName, "error.user", "영문자, 숫자, 특수문자를 각각 하나 이상 포함해야합니다.");
			boolean regexResult = newPw.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$");
			log.info("정규식 결과 : " + regexResult);
			log.info("비밀번호 유효성 검사 3차 오류");
			result = false;
			return false;
		}

		log.info("비밀번호 유효성 결과 : " + result);
		
		return true;
	}
	// /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$
	// 이메일 변경 시 유효성 & 정규식 검사
	public boolean emailError(Users user) {

		String newEmail = user.getEmail();

		boolean result = true;
		log.info("newEmail : " + newEmail);
	
		// 새 비밀번호가 숫자, 영문자, 특수문자 중 하나 이상이 빠져있거나 공백이 있다면 유효성 검사에 실패합니다.
		if (!newEmail.matches("^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$")) {
			boolean regexResult = newEmail.matches("^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$");
			log.info("정규식 결과 : " + regexResult);
			log.info("이메일 유효성 검사 2차 오류");
			result = false;
			return false;
		}

		log.info("이메일 유효성 결과 : " + result);
		
		return true;
	}
}
























