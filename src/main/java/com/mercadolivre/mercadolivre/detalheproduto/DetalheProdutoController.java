package com.mercadolivre.mercadolivre.detalheproduto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolivre.mercadolivre.novoproduto.Produto;

@RestController
@RequestMapping("/produtos/{id}")
public class DetalheProdutoController {
	
	@PersistenceContext
	private EntityManager manager;
	
	@GetMapping
	public DetalheProdutoView getProduto(@PathVariable("id") Long id) {
		Produto produtoDetalhe = manager.find(Produto.class, id);
		Assert.notNull(produtoDetalhe, "Não existe esse produto");
		return new DetalheProdutoView(produtoDetalhe);
	}

}
