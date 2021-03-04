package com.mercadolivre.mercadolivre.fechamentocompra;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.mercadolivre.mercadolivre.novoproduto.Produto;
import com.mercadolivre.mercadolivre.novousuario.Usuario;
import com.mercadolivre.mercadolivre.novousuario.UsuarioRepository;

@RestController
@RequestMapping(value = "/compras")
public class FechaCompraParte1Controller {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostMapping
	@Transactional
	public String fechamentoCompraParte1(@RequestBody @Valid NovaCompraRequest request,
			UriComponentsBuilder uriComponentsBuilder) throws BindException {
		Produto produtoASerComprado = manager.find(Produto.class, request.getIdProduto());

		int quantidade = request.getQuantidade();
		boolean abateu = produtoASerComprado.abataEstoque(quantidade);

		if (abateu) {
			Usuario comprador = usuarioRepository.findByLogin("teste@email.com").get();
			GatewayPagamento gateway = request.getGateway();
			Compra novaCompra = new Compra(produtoASerComprado, quantidade, comprador, gateway);
			manager.persist(novaCompra);
			
			return novaCompra.urlRedirecionamento(uriComponentsBuilder);
		}

		BindException problemaComEstoque = new BindException(request, "novaCompraRequest");
		problemaComEstoque.reject(null, "Não foi possível realizar a compra, sem estoque disponível");

		throw problemaComEstoque;
	}

}
