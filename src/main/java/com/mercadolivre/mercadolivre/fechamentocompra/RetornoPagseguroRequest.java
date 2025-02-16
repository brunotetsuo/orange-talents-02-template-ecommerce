package com.mercadolivre.mercadolivre.fechamentocompra;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RetornoPagseguroRequest implements RetornoGatewayPagamento {

	@NotBlank
	private String idTransacao;

	@NotNull
	private StatusRetornoPagseguro status;

	public RetornoPagseguroRequest(@NotBlank String idTransacao, @NotNull StatusRetornoPagseguro status) {
		this.idTransacao = idTransacao;
		this.status = status;
	}

	@Override
	public String toString() {
		return "RetornoPagseguroRequest [idTransacao=" + idTransacao + ", status=" + status + "]";
	}

	public Transacao toTransacao(Compra compra) {
		return new Transacao(status.normaliza(), idTransacao, compra);
	}

}
