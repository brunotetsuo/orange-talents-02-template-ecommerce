package com.mercadolivre.mercadolivre.fechamentocompra;

public interface RetornoGatewayPagamento {

	/**
	 * 
	 * @param compra
	 * @return uma nova transacao em função do gateway específico
	 */
	Transacao toTransacao(Compra compra);

}
