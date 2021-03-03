package com.mercadolivre.mercadolivre.novoproduto;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.mercadolivre.mercadolivre.novacategoria.Categoria;
import com.mercadolivre.mercadolivre.novousuario.Usuario;

public class ProdutoTest {

	@DisplayName("Produto precisa de no mínimo 3 características")
	@ParameterizedTest
	@MethodSource("geradorTeste1")
	void teste1(Collection<NovaCaracteristicaRequest> caracteristicas) throws Exception {
		Categoria categoria = new Categoria("categoria");
		Usuario dono = new Usuario("teste@email.com", "123456");

		new Produto("nome", BigDecimal.TEN, 10, "descricao", categoria, dono, caracteristicas);
	}

	static Stream<Arguments> geradorTeste1() {
		return Stream.of(Arguments.of(List.of(new NovaCaracteristicaRequest("key", "value"),
				new NovaCaracteristicaRequest("key2", "value2"), new NovaCaracteristicaRequest("key3", "value3"))),
				Arguments.of(List.of(new NovaCaracteristicaRequest("key", "value"),
						new NovaCaracteristicaRequest("key2", "value2"),
						new NovaCaracteristicaRequest("key3", "value3"),
						new NovaCaracteristicaRequest("key4", "value4"))));
	}

	@DisplayName("Produto não pode ser criado com menos de 3 características")
	@ParameterizedTest
	@MethodSource("geradorTeste2")
	void teste2(Collection<NovaCaracteristicaRequest> caracteristicas) throws Exception {
		Categoria categoria = new Categoria("categoria");
		Usuario dono = new Usuario("teste@email.com", "123456");

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Produto("nome", BigDecimal.TEN, 10, "descricao", categoria, dono, caracteristicas);
		});
	}

	static Stream<Arguments> geradorTeste2() {
		return Stream.of(
				Arguments.of(List.of(new NovaCaracteristicaRequest("key", "value"),
						new NovaCaracteristicaRequest("key2", "value2"))),
				Arguments.of(List.of(new NovaCaracteristicaRequest("key", "value"))));
	}

}
