package com.mercadolivre.mercadolivre.novousuario;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/usuarios")
public class NovoUsuarioController {
	
	@PersistenceContext
	private EntityManager manager;
	
	@PostMapping
	@Transactional
	public String criaUsuario(@RequestBody @Valid NovoUsuarioRequest request) {
		Usuario usuario = request.toModel();
		manager.persist(usuario);
		return usuario.toString();
	}

}
