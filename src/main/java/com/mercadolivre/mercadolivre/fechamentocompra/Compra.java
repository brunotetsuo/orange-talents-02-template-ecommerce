package com.mercadolivre.mercadolivre.fechamentocompra;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

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

	@OneToMany(mappedBy = "compra", cascade = CascadeType.MERGE)
	private Set<Transacao> transacoes = new HashSet<>();

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
				+ ", comprador=" + comprador + ", gatewayPagamento=" + gatewayPagamento + ", transacoes=" + transacoes
				+ "]";
	}

	public Long getId() {
		return id;
	}

	public Usuario getComprador() {
		return comprador;
	}

	public String urlRedirecionamento(UriComponentsBuilder uriComponentsBuilder) {
		return this.gatewayPagamento.criaUrlRetorno(this, uriComponentsBuilder);
	}

	public void adicionaTransacao(@Valid RetornoGatewayPagamento request) {
		Transacao novaTransacao = request.toTransacao(this);
		Assert.isTrue(!this.transacoes.contains(novaTransacao),
				"Já existe essa transação sendo processada " + novaTransacao);

		Assert.isTrue(transacoesConcluidasComSucesso().isEmpty(), "Essa compra já foi concluída com sucesso");

		this.transacoes.add(novaTransacao);
	}

	private Set<Transacao> transacoesConcluidasComSucesso() {
		Set<Transacao> transacoesConcluidasComSucesso = this.transacoes.stream().filter(Transacao::concluidaComSucesso)
				.collect(Collectors.toSet());
		Assert.isTrue(transacoesConcluidasComSucesso.size() <= 1,
				"Há mais de uma transação concluída com sucesso para essa compra " + this.id);
		return transacoesConcluidasComSucesso;
	}

	public boolean processadaComSucesso() {
		return !transacoesConcluidasComSucesso().isEmpty();
	}

	public Usuario getDonoProduto() {
		return produtoASerComprado.getDono();
	}

}
