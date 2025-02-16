package com.mercadolivre.mercadolivre.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.mercadolivre.mercadolivre.novousuario.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${jwt.expiration}")
	private String expiration;

	@Value("${jwt.secret}")
	private String secret;

	public String generateToken(Authentication authentication) {
		Usuario logged = (Usuario) authentication.getPrincipal();
		Date today = new Date();
		Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));

		// Em setSubject adicionamos a chave primária do usuário que ficará vinculada
		// ao Token. Poderia utilizar o email que também é único
		return Jwts.builder().setIssuer("Mercado Livre API").setSubject(logged.getId().toString()).setIssuedAt(today)
				.setExpiration(expirationDate).signWith(SignatureAlgorithm.HS256, secret).compact();
	}

	public boolean isValid(String token) {
		// Verifica se a assinatura do token bate com o algoritmo HS256
		// alimentado pelo secret em jwt.secret
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long getIdUser(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();

		// subject foi configurado em generateToken
		return Long.parseLong(claims.getSubject());
	}
}
