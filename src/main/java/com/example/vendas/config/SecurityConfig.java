package com.example.vendas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.vendas.security.jwt.JwtAuthFiltro;
import com.example.vendas.security.jwt.JwtService;
import com.example.vendas.service.impl.UsuarioServiceImpl;

// Classe de configuração do Spring Security

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UsuarioServiceImpl usuarioService;
	
	@Autowired
	private JwtService jwtService;
	
	
	@Bean //serve para criptografar e descriptografar a senha do usuário
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();//algoritmo de cripto
	}
	
	// Registrando o filtro jwt
	public OncePerRequestFilter jwtFilter() {
		return new JwtAuthFiltro(jwtService, usuarioService);
	}
	
	//dois métodos para realizar a configuração
	
	@Override //método para autenticação, define como o usuário faz login
	protected void configure(AuthenticationManagerBuilder auth)throws Exception{
		// DADOS DO BANCO
		auth
		.userDetailsService(usuarioService)
		.passwordEncoder(passwordEncoder());
		
// DADOS EM MEMÓRIA	
//		auth.inMemoryAuthentication()
//		.passwordEncoder(passwordEncoder())
//		.withUser("Silva")
//		.password(passwordEncoder().encode("123"))
//		.roles("USER", "ADMIN");
	}
	
	@Override //método para autorização, define quem tem acesso ao que
	protected void configure(HttpSecurity http)throws Exception{
		http
			.csrf().disable() // esta desabilitado, pois não vamos atender usuários do navegador
			.authorizeRequests()
				.antMatchers("/api/clientes/**")
					.hasAnyRole("USER", "ADMIN")
				.antMatchers("/api/produtos/**")
					.hasRole("ADMIN")
				.antMatchers("/api/pedidos/**")
					.hasAnyRole("USER", "ADMIN")
				.antMatchers(HttpMethod.POST, "/api/usuarios/**")
					.permitAll()
				.anyRequest().authenticated()// caso esqueça de autenticar
			.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
		
				//aplicação passa a ser stateless e executa 
				//filtro criando antes do filtro existente no Spring Security
				//Agora sera usado o token para autenticar o usuario	
	
	}
}











