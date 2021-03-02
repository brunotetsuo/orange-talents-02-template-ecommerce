package com.mercadolivre.mercadolivre.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

import com.mercadolivre.mercadolivre.novousuario.Usuario;

@Configuration
public class AppUserDetailsMapper implements UserDetailsMapper {

	@Override
	public UserDetails map(Object shouldBeASystemUser) {
		return new UsuarioLogado((Usuario) shouldBeASystemUser);
	}

}
