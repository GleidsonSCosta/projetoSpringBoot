package com.example.vendas.rest.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.vendas.model.Usuarios;
import com.example.vendas.service.impl.UsuarioServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuariosController {

	private final UsuarioServiceImpl usuarioService;
	private final PasswordEncoder passwordEncoder;
	
//  Método para salvar o usuário, também faz a criptografia da senha
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Usuarios salvar (@RequestBody @Valid Usuarios usuario) {
		String senhaCripto = passwordEncoder.encode(usuario.getSenha());
		usuario.setSenha(senhaCripto);
		return usuarioService.salvar(usuario);
	}
	
}
