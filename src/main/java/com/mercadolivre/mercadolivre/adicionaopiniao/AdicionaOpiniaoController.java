package com.mercadolivre.mercadolivre.adicionaopiniao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolivre.mercadolivre.novoproduto.Produto;
import com.mercadolivre.mercadolivre.novousuario.Usuario;
import com.mercadolivre.mercadolivre.novousuario.UsuarioRepository;

@RestController
@RequestMapping(value = "/produtos/{id}/opinioes")
public class AdicionaOpiniaoController {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostMapping
	@Transactional
	public String addOpiniao(@RequestBody @Valid NovaOpiniaoRequest request, @PathVariable("id") Long id) {
		Produto produto = manager.find(Produto.class, id);
		Usuario consumidor = usuarioRepository.findByLogin("teste@email.com").get();
		Opiniao novaOpiniao = request.toModel(produto, consumidor);
		manager.persist(novaOpiniao);
		return novaOpiniao.toString();
	}
}
