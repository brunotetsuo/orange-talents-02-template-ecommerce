package com.mercadolivre.mercadolivre.novacategoria;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@Profile("test")
public class NovaCategoriaControllerTest {
	
	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@PersistenceContext
	private EntityManager manager;
	

	private String toJson(Categoria request) throws JsonProcessingException {
		return objectMapper.writeValueAsString(request);
	}
	
	@Test
	@DisplayName("Deve criar uma nova categoria e deve retornar status 200")
	void deveCriarUmaNovaCategoria() throws Exception {

		Categoria novaCategoria = new Categoria("Eletrônicos");
		mockMvc.perform(post("/categorias").contentType(MediaType.APPLICATION_JSON)
				.content(toJson(novaCategoria))).andExpect(status().isOk());

		List<Categoria> categorias = manager.createQuery("SELECT c FROM Categoria c", Categoria.class).getResultList();

		Categoria categoria = categorias.get(1);

		assertAll(
				() -> assertNotNull(categorias),
				() -> assertTrue(categorias.size() == 2),
				() -> assertEquals("Eletrônicos", categoria.getNome())
		);
	}

	@Test
	@DisplayName("Devolver status 400 caso nome em branco")
	void deveDevolver400NomeBranco() throws Exception {

		Categoria novaCategoria = new Categoria("");
		mockMvc.perform(post("/categorias").contentType(MediaType.APPLICATION_JSON)
				.content(toJson(novaCategoria))).andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Devolver status 400 caso nome já exista")
	void nomeNaoPodeSerDuplicado() throws Exception {

		Categoria novaCategoria = new Categoria("Informática");
		mockMvc.perform(post("/categorias").contentType(MediaType.APPLICATION_JSON)
				.content(toJson(novaCategoria))).andExpect(status().isOk());
		
		Categoria novaCategoria2 = new Categoria("Informática");
		mockMvc.perform(post("/categorias").contentType(MediaType.APPLICATION_JSON)
				.content(toJson(novaCategoria2))).andExpect(status().isBadRequest());
	}
}
