package com.mercadolivre.mercadolivre.fechamentocompra;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FechamentoCompraParte2Controller {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private EventosNovaCompra eventosNovaCompra;

	@PostMapping(value = "/retorno-pagseguro/{id}")
	@Transactional
	public String pagamentoPagSeguro(@PathVariable("id") Long idCompra, @Valid RetornoPagseguroRequest request) {
		return processa(idCompra, request);
	}

	@PostMapping(value = "/retorno-paypal/{id}")
	@Transactional
	public String pagamentoPaypal(@PathVariable("id") Long idCompra, @Valid RetornoPaypalRequest request) {
		return processa(idCompra, request);
	}

	private String processa(Long idCompra, RetornoGatewayPagamento retornoGatewayPagamento) {
		Compra compra = manager.find(Compra.class, idCompra);
		compra.adicionaTransacao(retornoGatewayPagamento);
		manager.merge(compra);

		eventosNovaCompra.processa(compra);

		return compra.toString();
	}

}
