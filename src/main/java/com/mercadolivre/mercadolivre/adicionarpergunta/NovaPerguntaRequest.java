package com.mercadolivre.mercadolivre.adicionarpergunta;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.mercadolivre.mercadolivre.novoproduto.Produto;
import com.mercadolivre.mercadolivre.novousuario.Usuario;

public class NovaPerguntaRequest {

	@NotBlank
	private String titulo;

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Override
	public String toString() {
		return "NovaPerguntaRequest [titulo=" + titulo + "]";
	}

	public Pergunta toModel(@NotNull @Valid Usuario interessada, @NotNull @Valid Produto produto) {
		return new Pergunta(titulo, interessada, produto);
	}

}
