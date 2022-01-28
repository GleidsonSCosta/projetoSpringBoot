package com.example.vendas.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
public class NotEmptylistValidator implements ConstraintValidator<NotEmptyList, List> {

	@Override
	public boolean isValid(List list, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		return list !=  null && !list.isEmpty();
	}
	
	@Override
	public void initialize(NotEmptyList constraintAnnotation) {
		
	}

}
