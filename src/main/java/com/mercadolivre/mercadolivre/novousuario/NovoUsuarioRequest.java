package com.mercadolivre.mercadolivre.novousuario;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NovoUsuarioRequest {
	
	@NotBlank
	@Email
	private String login;
	
	@NotBlank
	@Size(min = 6)
	private String senha;

	public NovoUsuarioRequest(@NotBlank @Email String login, @NotBlank @Size(min = 6) String senha) {
		this.login = login;
		this.senha = senha;
	}

	public String getLogin() {
		return login;
	}
	
	public String getSenha() {
		return senha;
	}

	public Usuario toModel() {
		return new Usuario(this.login, this.senha);
	}
}
