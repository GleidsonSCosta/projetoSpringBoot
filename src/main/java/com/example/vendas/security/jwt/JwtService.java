package com.example.vendas.security.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import com.example.vendas.VendasApplication;
import com.example.vendas.model.Usuarios;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service	
public class JwtService {
	
	@Value("${security.jwt.expiracao}")
	private String expiracao;
	
	@Value("${security.jwt.chave-assinatura}")
	private String chaveAssinatura;
	
	
	//método para gerar o token JWT
	public String gerarToken(Usuarios usuario) {
		long expString = Long.valueOf(expiracao);
		LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);//pega a hora autal mais a expiração
		Date data = Date.from(dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant());
		//retornando o teken
		return Jwts
				.builder()
				.setSubject(usuario.getLogin())
				.setExpiration(data)
				.signWith(SignatureAlgorithm.HS512, chaveAssinatura)
				.compact();// pega todas as informações acima para gerar o token
				
	}
	
	//método para decodificar o token através dos Claims - "informações do no body do token"
	private Claims obterClaims (String token) throws ExpiredJwtException{
		return Jwts
				.parser()
				.setSigningKey(chaveAssinatura)
				.parseClaimsJws(token)
				.getBody();
	}
	
	//verificar se o token esta válido
	public boolean tokenValido(String token) {
		try {
			Claims claims = obterClaims(token);
			Date dataExpiracao = claims.getExpiration();
			LocalDateTime localDateTime = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			return !LocalDateTime.now().isAfter(localDateTime);
		}
		catch (Exception e) {
			return false;
		}
	}
	
	//obter login do usuario
	public String obterLoginUsuario (String token) throws ExpiredJwtException {
		return (String) obterClaims(token).getSubject();
	}

//	public static void main(String[] args) {
//		ConfigurableApplicationContext contexto = SpringApplication.run(VendasApplication.class);
//		JwtService service = contexto.getBean(JwtService.class);
//		Usuarios usuario = Usuarios.builder().login("gleidson").build();
//		String token = service.gerarToken(usuario);
//		System.out.println(token);
//		
//		boolean isTokenValido = service.tokenValido(token);
//		System.err.println("O teken é válido? " + isTokenValido);
//		
//		System.err.println(service.obterLoginUsuario(token));
//	}

//	public static void main(String[] args) {
//		ConfigurableApplicationContext contexto = SpringApplication.run(VendasApplication.class);
//		JwtService service = contexto.getBean(JwtService.class);
//		Usuarios usuario = Usuarios.builder().login("gleidson").build();
//		String token = service.gerarToken(usuario);
//		System.out.println(token);
//		
//		boolean isTokenValido = service.tokenValido(token);
//		System.err.println("O teken é válido? " + isTokenValido);
//		
//		System.err.println(service.obterLoginUsuario(token));
//	}

	
}













