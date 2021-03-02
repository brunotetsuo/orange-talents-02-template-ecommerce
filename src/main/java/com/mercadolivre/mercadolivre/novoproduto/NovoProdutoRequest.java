package com.mercadolivre.mercadolivre.novoproduto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.mercadolivre.mercadolivre.novacategoria.Categoria;
import com.mercadolivre.mercadolivre.novousuario.Usuario;
import com.mercadolivre.mercadolivre.validations.ExistsId;
import com.mercadolivre.mercadolivre.validations.UniqueValue;

public class NovoProdutoRequest {

	@NotBlank
	@UniqueValue(domainClass = Produto.class, fieldName = "nome", message = "Já existe Produto com esse nome")
	private String nome;

	@NotNull
	@Positive
	private BigDecimal valor;

	@Positive
	private Integer quantidade;

	@NotBlank
	@Length(max = 1000)
	private String descricao;

	@NotNull
	@ExistsId(domainClass = Categoria.class, fieldName = "id")
	private Long idCategoria;

	@Size(min = 3)
	@Valid
	private List<NovaCaracteristicaRequest> caracteristicas = new ArrayList<>();

	public NovoProdutoRequest(@NotBlank String nome, @NotNull @Positive BigDecimal valor, @Positive Integer quantidade,
			@NotBlank @Length(max = 1000) String descricao, @NotNull Long idCategoria,
			List<NovaCaracteristicaRequest> caracteristicas) {
		super();
		this.nome = nome;
		this.valor = valor;
		this.quantidade = quantidade;
		this.descricao = descricao;
		this.idCategoria = idCategoria;
		this.caracteristicas.addAll(caracteristicas);
	}

	public List<NovaCaracteristicaRequest> getCaracteristicas() {
		return caracteristicas;
	}

	@Override
	public String toString() {
		return "NovoProdutoRequest [nome=" + nome + ", valor=" + valor + ", quantidade=" + quantidade + ", descricao="
				+ descricao + ", idCategoria=" + idCategoria + ", caracteristicas=" + caracteristicas + "]";
	}

	public Produto toModel(EntityManager manager, Usuario dono) {
		Categoria categoria = manager.find(Categoria.class, idCategoria);
		return new Produto(this.nome, this.valor, this.quantidade, this.descricao, categoria, dono, caracteristicas);
	}

	public Set<String> buscaCaracteristicasIguais() {
		Set<String> nomesIguais = new HashSet<>();
		Set<String> resultados = new HashSet<>();
		for (NovaCaracteristicaRequest caracteristica : caracteristicas) {
			String nome = caracteristica.getNome();
			if (!nomesIguais.add(nome)) {
				resultados.add(nome);
			}
		}
		return resultados;
	}

}
