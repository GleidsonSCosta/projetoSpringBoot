package com.example.vendas.rest.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.vendas.exception.SenhaInvalidaException;
import com.example.vendas.model.Usuarios;
import com.example.vendas.rest.dto.CredencialDTO;
import com.example.vendas.rest.dto.TokenDTO;
import com.example.vendas.security.jwt.JwtService;
import com.example.vendas.service.impl.UsuarioServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuariosController {

	private final UsuarioServiceImpl usuarioService;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	

//  Método para salvar o usuário, também faz a criptografia da senha

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Usuarios salvar (@RequestBody @Valid Usuarios usuario) {
		String senhaCripto = passwordEncoder.encode(usuario.getSenha());
		usuario.setSenha(senhaCripto);
		return usuarioService.salvar(usuario);
	}


	@PostMapping("/auth")
	public TokenDTO autenticar(@RequestBody CredencialDTO credenciais) {
		try {
			Usuarios usuario = Usuarios.builder()
					.login(credenciais.getLogin())
					.senha(credenciais.getSenha())
					.build();
			UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);

			String token = jwtService.gerarToken(usuario);

			return new TokenDTO(usuario.getLogin(), token);
		} catch (UsernameNotFoundException | SenhaInvalidaException e) {
			throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		}
	}
}
