package com.mercadolivre.mercadolivre.novacategoria;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categorias")
public class NovaCategoriaController {

	@PersistenceContext
	private EntityManager manager;

	@PostMapping
	@Transactional
	public String criaCategoria(@RequestBody @Valid NovaCategoriaRequest request) {
		Categoria novaCategoria = request.toModel(manager);
		manager.persist(novaCategoria);
		return novaCategoria.toString();
	}

}
