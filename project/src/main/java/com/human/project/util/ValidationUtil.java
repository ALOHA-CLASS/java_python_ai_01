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
	

}
























