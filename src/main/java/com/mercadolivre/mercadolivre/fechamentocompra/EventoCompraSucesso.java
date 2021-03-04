package com.mercadolivre.mercadolivre.fechamentocompra;
/**
 * Todo evendo relacionado ao sucesso de uma nova compra deve implementar essa interface
 * @author Bruno.rocha
 *
 */
public interface EventoCompraSucesso {

	void processa(Compra compra);

}
