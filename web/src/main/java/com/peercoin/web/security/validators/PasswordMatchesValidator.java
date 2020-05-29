package com.peercoin.web.security.validators;

import com.peercoin.web.models.dtos.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches,UserDto> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext context){
        return userDto.getPassword().equals(userDto.getConfirmPassword());
    }
}
