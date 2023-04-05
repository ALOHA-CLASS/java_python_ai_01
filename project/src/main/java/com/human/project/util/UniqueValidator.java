package com.human.project.util;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.human.project.domain.Users;
import com.human.project.mapper.UserMapper;

public class UniqueValidator implements ConstraintValidator<Unique, Users> {
    @Autowired
    private UserMapper userMapper;        
    @Override
    public boolean isValid(Users user, ConstraintValidatorContext context) {        
    	try {
            Users selectedUser = userMapper.select(user);
            return selectedUser == null; // 이미 있는 경우 false를 반환하고, 없는 경우 true를 반환
        } catch (Exception e) {
            // 예외가 발생한 경우 유효성 검사를 실패하게 합니다.
            return false;
        }
    }
}