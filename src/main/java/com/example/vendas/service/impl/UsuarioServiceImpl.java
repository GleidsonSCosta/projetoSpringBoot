package com.example.vendas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.vendas.exception.SenhaInvalidaException;
import com.example.vendas.model.Usuarios;
import com.example.vendas.repository.UsuariosRepository;

@Service
public class UsuarioServiceImpl implements UserDetailsService {
	
	//Classe usada para carregar usuário da base de dados
	
	@Autowired 
	private PasswordEncoder encoder;
	
	@Autowired
	private UsuariosRepository repository;
	
	@Transactional
	public Usuarios salvar(Usuarios usuario) {
		return repository.save(usuario);
	}
	
	// método para fazer a comparação da senha vinda do DTO com a que foi passada
	public UserDetails autenticar(Usuarios usuario) {
		UserDetails user = loadUserByUsername(usuario.getLogin());
		boolean senhaCorreta = encoder.matches(usuario.getSenha(), user.getPassword());
		
		if(senhaCorreta) {
			return user;
		}
		
		throw new SenhaInvalidaException();
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuarios usuario = repository.findByLogin(username)
		.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontado na base de dados."));
		
		String[] roles = usuario.isAdmin() ? new String[] {"ADMIN", "USER"} : new String[] {"USER"};
		
		return User
				.builder()
				.username(usuario.getLogin())
				.password(usuario.getSenha())
				.roles(roles)
				.build();
		
		
		
		
//		 simulando usuário vindo do banco		
		
//		if (!username.equals("Maria")) {
//			throw new UsernameNotFoundException("Usuário não encontrado na base de dados.");
//		}
//		

//		return User
//				.builder()
//				.username("Maria")
//				.password(encoder.encode("321"))
//				.roles("USER", "ADMIN")
//				.build();
	}


}
