package com.example.vendas.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.vendas.service.impl.UsuarioServiceImpl;

public class JwtAuthFiltro extends OncePerRequestFilter {

	private JwtService jwtService;
	private UsuarioServiceImpl usuarioService;
	
	public JwtAuthFiltro(JwtService jwtService, UsuarioServiceImpl usuarioService) {
		this.jwtService = jwtService;
		this.usuarioService = usuarioService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//captura o requizição
		String authorization = request.getHeader("Authorization");
		
		if(authorization != null && authorization.startsWith("Bearer")){
			String token = authorization.split(" ")[1];
			boolean isValid= jwtService.tokenValido(token);
			
			if(isValid) {
				String loginUsuario = jwtService.obterLoginUsuario(token);
				
				// carrega o usuário com sua role e authorities	
				UserDetails usuario = usuarioService.loadUserByUsername(loginUsuario);
				
				UsernamePasswordAuthenticationToken user = new 
						UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
				
				//isso diz para o Spring Security que setrata de uma autenticação web
				user.setDetails( new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(user);
			}
			
			filterChain.doFilter(request, response);
		}
		
	}

}
