package com.mercadolivre.mercadolivre.novoproduto;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import com.mercadolivre.mercadolivre.novacategoria.Categoria;
import com.mercadolivre.mercadolivre.novousuario.Usuario;

@Entity
@Table(name = "produtos")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private @NotBlank String nome;
	private @NotNull @Positive BigDecimal valor;
	private @Positive Integer quantidade;
	private @NotBlank @Length(max = 1000) String descricao;
	@NotNull
	@Valid
	@ManyToOne
	private Categoria categoria;
	@NotNull
	@Valid
	@ManyToOne
	private Usuario dono;

	@OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST)
	private Set<CaracteristicaProduto> caracteristicas = new HashSet<>();

	@Deprecated
	public Produto() {
	}

	public Produto(@NotBlank String nome, @NotNull @Positive BigDecimal valor, @Positive Integer quantidade,
			@NotBlank @Length(max = 1000) String descricao, @NotNull @Valid Categoria categoria,
			@NotNull @Valid Usuario dono, @Size(min = 3) @Valid Collection<NovaCaracteristicaRequest> caracteristicas) {
		this.nome = nome;
		this.valor = valor;
		this.quantidade = quantidade;
		this.descricao = descricao;
		this.categoria = categoria;
		this.dono = dono;
		this.caracteristicas.addAll(caracteristicas.stream().map(caracteristica -> caracteristica.toModel(this))
				.collect(Collectors.toSet()));
		
		Assert.isTrue(this.caracteristicas.size() >=3, "Todo produto precisar ter no mínimo 3 características");
	}

	@Override
	public String toString() {
		return "Produto [id=" + id + ", nome=" + nome + ", valor=" + valor + ", quantidade=" + quantidade
				+ ", descricao=" + descricao + ", categoria=" + categoria + ", dono=" + dono + ", caracteristicas="
				+ caracteristicas + "]";
	}

}
