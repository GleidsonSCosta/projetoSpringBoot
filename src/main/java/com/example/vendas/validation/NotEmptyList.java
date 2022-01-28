package com.example.vendas.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)// é verificada em tempo de execução
@Target(ElementType.FIELD) // diz que a anotação é colocada sobre um campo
@Constraint( validatedBy = { NotEmptylistValidator.class } ) //diz que é uma annotation de validação
public @interface NotEmptyList {

	String message() default "A lista não pode se vazia";
	
	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
