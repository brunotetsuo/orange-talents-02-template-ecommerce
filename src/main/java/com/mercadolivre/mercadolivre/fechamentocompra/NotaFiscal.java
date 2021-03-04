package com.mercadolivre.mercadolivre.fechamentocompra;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.jsonwebtoken.lang.Assert;

@Service
public class NotaFiscal implements EventoCompraSucesso {

	@Override
	public void processa(Compra compra) {
		Assert.isTrue(compra.processadaComSucesso(), "Compra não concluída com sucesso "+ compra);
		
		RestTemplate restTemplate = new RestTemplate();
		Map<String, Object> request = Map.of("idCompra", compra.getId(), "idComprador", compra.getComprador().getId());
		restTemplate.postForEntity("http://localhost:8080/notas-fiscais", request, String.class);
	}

}
