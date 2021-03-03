package com.mercadolivre.mercadolivre.adicionarpergunta;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.mercadolivre.mercadolivre.novoproduto.Produto;
import com.mercadolivre.mercadolivre.novousuario.Usuario;

@Entity
public class Pergunta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private @NotBlank String titulo;

	private LocalDateTime instanteCriacao;

	@ManyToOne
	private @NotNull @Valid Usuario interessada;

	@ManyToOne
	private @NotNull @Valid Produto produto;

	@Deprecated
	public Pergunta() {
	}

	public Pergunta(@NotBlank String titulo, @NotNull @Valid Usuario interessada, @NotNull @Valid Produto produto) {
		this.titulo = titulo;
		this.interessada = interessada;
		this.produto = produto;
		this.instanteCriacao = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "Pergunta [id=" + id + ", titulo=" + titulo + ", instanteCriacao=" + instanteCriacao + ", interessada="
				+ interessada + ", produto=" + produto + "]";
	}

	public Usuario getInteressada() {
		return interessada;
	}

	public Usuario getDonoProduto() {
		return produto.getDono();
	}

}
