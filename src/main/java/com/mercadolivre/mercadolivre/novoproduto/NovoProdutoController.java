package com.mercadolivre.mercadolivre.novoproduto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolivre.mercadolivre.novousuario.Usuario;

@RestController
@RequestMapping(value = "/produtos")
public class NovoProdutoController {
	
	@PersistenceContext
	private EntityManager manager;
	
	@InitBinder
	public void init(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(new ProibideCaracteristaComNomeIgualValidator());
	}
	
	@PostMapping
	@Transactional
	public String criaProduto(@RequestBody @Valid NovoProdutoRequest request) {
		Usuario dono = manager.find(Usuario.class, 1L);
		Produto novoProduto = request.toModel(manager, dono);
		manager.persist(novoProduto);
		return novoProduto.toString();
	}

}
