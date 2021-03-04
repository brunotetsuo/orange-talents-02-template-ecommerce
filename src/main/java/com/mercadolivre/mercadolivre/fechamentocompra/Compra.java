package com.mercadolivre.mercadolivre.fechamentocompra;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.mercadolivre.mercadolivre.novoproduto.Produto;
import com.mercadolivre.mercadolivre.novousuario.Usuario;

@Entity
public class Compra {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@NotNull
	@Valid
	private Produto produtoASerComprado;

	@Positive
	private int quantidade;

	@ManyToOne
	@NotNull
	@Valid
	private Usuario comprador;

	@NotNull
	private GatewayPagamento gatewayPagamento;

	@Deprecated
	public Compra() {
	}

	public Compra(@NotNull @Valid Produto produtoASerComprado, @Positive int quantidade,
			@NotNull @Valid Usuario comprador, @NotNull GatewayPagamento gatewayPagamento) {
		this.produtoASerComprado = produtoASerComprado;
		this.quantidade = quantidade;
		this.comprador = comprador;
		this.gatewayPagamento = gatewayPagamento;
	}

	@Override
	public String toString() {
		return "Compra [id=" + id + ", produtoASerComprado=" + produtoASerComprado + ", quantidade=" + quantidade
				+ ", comprador=" + comprador + ", gatewayPagamento=" + gatewayPagamento + "]";
	}

	public Long getId() {
		return id;
	}

}
