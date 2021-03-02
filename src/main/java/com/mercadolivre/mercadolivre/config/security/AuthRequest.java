package com.mercadolivre.mercadolivre.config.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class AuthRequest {

	private String login;
	private String senha;

	public AuthRequest(String login, String senha) {
		this.login = login;
		this.senha = senha;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return senha;
	}

	public UsernamePasswordAuthenticationToken convert() {
		return new UsernamePasswordAuthenticationToken(login, senha);
	}
}
