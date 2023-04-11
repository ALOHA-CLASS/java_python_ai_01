package com.human.project.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import org.apache.commons.beanutils.PropertyUtils;
import com.human.project.domain.Users;
import com.human.project.mapper.UserMapper;

public class UniqueUserValidator implements ConstraintValidator<Unique, String> {
    @Autowired
    private UserMapper userMapper;

    private Class<?> domainClass;
    private String email;

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.domainClass = constraintAnnotation.domainClass();
        this.email = constraintAnnotation.email();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            Users user = new Users();
            PropertyUtils.setProperty(user, this.email, value);
            Users selectedUser = userMapper.select(user);
            return selectedUser == null || selectedUser.getUserNo() == user.getUserNo();
        } catch (Exception e) {
            return false;
        }
    }
}