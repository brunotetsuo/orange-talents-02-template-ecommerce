package com.mercadolivre.mercadolivre.novacategoria;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;

import org.springframework.util.Assert;

import com.mercadolivre.mercadolivre.validations.UniqueValue;

public class NovaCategoriaRequest {

	@NotBlank
	@UniqueValue(domainClass = Categoria.class, fieldName = "nome", message = "Já existe essa categoria")
	private String nome;

	private Long idCategoriaMae;

	public NovaCategoriaRequest(String nome, Long idCategoriaMae) {
		this.setNome(nome);
		this.setIdCategoriaMae(idCategoriaMae);
	}

	public void setIdCategoriaMae(Long idCategoriaMae) {
		this.idCategoriaMae = idCategoriaMae;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Categoria toModel(EntityManager manager) {
		Assert.hasText(nome, "Nome não pode estar em branco");
		Categoria categoria = new Categoria(nome);
		if (idCategoriaMae != null) {
			Categoria categoriaMae = manager.find(Categoria.class, idCategoriaMae);

			Assert.notNull(categoriaMae, "Id da categoria mãe não pode ser inválido!");

			categoria.setCategoriaMae(categoriaMae);
		}
		return categoria;
	}

	@Override
	public String toString() {
		return "NovaCategoriaRequest [nome=" + nome + ", idCategoriaMae=" + idCategoriaMae + "]";
	}

}
