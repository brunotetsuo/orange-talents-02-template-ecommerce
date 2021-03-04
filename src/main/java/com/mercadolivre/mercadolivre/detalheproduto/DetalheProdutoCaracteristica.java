package com.mercadolivre.mercadolivre.detalheproduto;

import com.mercadolivre.mercadolivre.novoproduto.CaracteristicaProduto;

public class DetalheProdutoCaracteristica {

	private String nome;
	private String descricao;

	public DetalheProdutoCaracteristica(CaracteristicaProduto caracteristicas) {
		this.nome = caracteristicas.getNome();
		this.descricao = caracteristicas.getDescricao();
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

}
