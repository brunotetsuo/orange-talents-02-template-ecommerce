package com.mercadolivre.mercadolivre.fechamentocompra;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventosNovaCompra {

	@Autowired
	private Set<EventoCompraSucesso> eventosCompraSucesso;

	public void processa(Compra compra) {
		if (compra.processadaComSucesso()) {
			eventosCompraSucesso.forEach(evento -> evento.processa(compra));
		}
		else {
			
		}
	}

}
