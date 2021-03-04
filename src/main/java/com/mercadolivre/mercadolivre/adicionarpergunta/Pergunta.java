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
public class Pergunta implements Comparable<Pergunta>{

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
	
	public String getTitulo() {
		return titulo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((interessada == null) ? 0 : interessada.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pergunta other = (Pergunta) obj;
		if (interessada == null) {
			if (other.interessada != null)
				return false;
		} else if (!interessada.equals(other.interessada))
			return false;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}

	@Override
	public int compareTo(Pergunta o) {
		return this.titulo.compareTo(o.titulo);
	}

}
