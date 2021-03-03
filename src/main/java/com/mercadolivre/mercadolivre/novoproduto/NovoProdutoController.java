package com.mercadolivre.mercadolivre.novoproduto;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mercadolivre.mercadolivre.novousuario.Usuario;
import com.mercadolivre.mercadolivre.novousuario.UsuarioRepository;

@RestController
@RequestMapping(value = "/produtos")
public class NovoProdutoController {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private Uploader uploaderFake;
	
	@InitBinder(value = "novoProdutoRequest")
	public void init(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(new ProibideCaracteristaComNomeIgualValidator());
	}
	
	@PostMapping
	@Transactional
	public String criaProduto(@RequestBody @Valid NovoProdutoRequest request) {
		// Simula usuario logado
		Usuario dono = usuarioRepository.findByLogin("teste@email.com").get();
		Produto novoProduto = request.toModel(manager, dono);
		manager.persist(novoProduto);
		return novoProduto.toString();
	}
	
	@PostMapping(value = "/{id}/imagens")
	@Transactional
	public String addImagens(@PathVariable("id") Long id, @Valid NovasImagensRequest request) {
		/*
		 * 1) enviar imagens para o local onde elas vão ficar
		 * 2) pegar os links de todas as imagens
		 * 3) associar esses links com o produto em questão
		 * 4) preciso carregar o produto
		 * 5) depois que associar eu preciso atualizar a nova versão do produto
		 */
		
		Usuario dono = usuarioRepository.findByLogin("teste@email.com").get();
		Produto produto = manager.find(Produto.class, id);
		
		if(!produto.pertenceAoUsuario(dono)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
		
		Set<String> links = uploaderFake.envia(request.getImagens());
		produto.associaImagens(links);
		
		manager.merge(produto);
		
		return produto.toString();
	}

}
