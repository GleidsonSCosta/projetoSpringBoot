package com.example.vendas.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.vendas.exception.PedidoNaoEncontradoException;
import com.example.vendas.exception.RegraNegocioException;
import com.example.vendas.rest.ApiErrors;

@RestControllerAdvice
public class ApplicationControllerAdvace {
	
	@ExceptionHandler(RegraNegocioException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleRegranegocioException(RegraNegocioException ex) {
		String mensagemErro = ex.getMessage();
		return new ApiErrors(mensagemErro);
	}
	
	@ExceptionHandler(PedidoNaoEncontradoException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiErrors handlePedidoNaoEncontratoException(PedidoNaoEncontradoException ex) {
		return new ApiErrors(ex.getMessage());
	}
}
