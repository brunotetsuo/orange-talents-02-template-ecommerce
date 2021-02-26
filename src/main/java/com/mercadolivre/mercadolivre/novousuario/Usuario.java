package com.mercadolivre.mercadolivre.novousuario;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Email
	private String login;

	@NotBlank
	@Size(min = 6)
	private String senha;

	private LocalDateTime instanteCriado = LocalDateTime.now();

	@Deprecated
	public Usuario() {
	}

	public Usuario(@NotBlank @Email String login, @NotBlank @Size(min = 6) String senha) {
		Assert.hasText(login, "Login não pode estar em branco");
		Assert.state(senha.length() >=6, "A senha deve ter no mínimo 6 caracteres");
		
		String senhaHash = new BCryptPasswordEncoder().encode(senha);
		
		this.login = login;
		this.senha = senhaHash;
	}

	public String getLogin() {
		return login;
	}

	public String getSenha() {
		return senha;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", login=" + login + ", senha=" + senha + ", instanteCriado=" + instanteCriado
				+ "]";
	}

}
