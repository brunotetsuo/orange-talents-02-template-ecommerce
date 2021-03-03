package com.mercadolivre.mercadolivre.adicionarpergunta;

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
@RequestMapping(value = "/produtos/{id}/perguntas")
public class AdicionaPerguntaController {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private Emails emails;

	@PostMapping
	@Transactional
	public String addPergunta(@RequestBody @Valid NovaPerguntaRequest request, @PathVariable("id") Long id) {
		Produto produto = manager.find(Produto.class, id);
		Usuario interessada = usuarioRepository.findByLogin("teste2@email.com").get();
		Pergunta novaPergunta = request.toModel(interessada, produto);
		manager.persist(novaPergunta);
		
		emails.novaPergunta(novaPergunta);
		
		return novaPergunta.toString();
	}

}
