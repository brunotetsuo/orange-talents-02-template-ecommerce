package com.mercadolivre.mercadolivre.novousuario;

import static org.junit.jupiter.api.Assertions.*;
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
public class NovoUsuarioControllerTest {
	
	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@PersistenceContext
	private EntityManager manager;
	

	private String toJson(NovoUsuarioRequest request) throws JsonProcessingException {
		return objectMapper.writeValueAsString(request);
	}
	
	@Test
	@DisplayName("Deve criar um novo usuario e deve retornar status 200")
	void deveCriarUmNovoUsuario() throws Exception {

		NovoUsuarioRequest request = new NovoUsuarioRequest("bruno@email.com", "123456");
		mockMvc.perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON)
				.content(toJson(request))).andExpect(status().isOk());

		List<Usuario> usuarios = manager.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();

		Usuario usuario = usuarios.get(1);

		assertAll(
				() -> assertNotNull(usuarios),
				() -> assertTrue(usuarios.size() == 2),
				() -> assertEquals("bruno@email.com", usuario.getLogin())
		);
	}

	
	@Test
	@DisplayName("Devolver status 400 caso login em branco")
	void deveDevolver400LoginBranco() throws Exception {

		NovoUsuarioRequest request = new NovoUsuarioRequest("", "123456");
		mockMvc.perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON)
				.content(toJson(request))).andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Devolver status 400 caso login não esteja em formato de email")
	void deveDevolver400LoginSemFormatoEmail() throws Exception {

		NovoUsuarioRequest request = new NovoUsuarioRequest("brunoemail.com", "123456");
		mockMvc.perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON)
				.content(toJson(request))).andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Devolver status 400 caso a senha esteja em branco")
	void deveDevolver400SenhaBranco() throws Exception {

		NovoUsuarioRequest request = new NovoUsuarioRequest("bruno@email.com", "");
		mockMvc.perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON)
				.content(toJson(request))).andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Devolver status 400 caso a senha tenha menos que 6 caracteres")
	void deveDevolver400SenhaMenos6Caracteres() throws Exception {

		NovoUsuarioRequest request = new NovoUsuarioRequest("bruno@email.com", "12345");
		mockMvc.perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON)
				.content(toJson(request))).andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Deve criar um novo usuario com Hash de senha")
	void deveCriarUmNovoUsuarioComHashSenha() throws Exception {

		Usuario usuario = new Usuario("teste@email.com", "123456");

		assertNotEquals("123456", usuario.getSenha());
	}
	
	@Test
	@DisplayName("Devolver status 400 caso login já exista")
	void loginNaoPodeSerDuplicado() throws Exception {

		NovoUsuarioRequest request = new NovoUsuarioRequest("teste@email.com", "123456");
		mockMvc.perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON)
				.content(toJson(request))).andExpect(status().isOk());
		NovoUsuarioRequest request2 = new NovoUsuarioRequest("teste@email.com", "123456");
		mockMvc.perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON)
				.content(toJson(request2))).andExpect(status().isBadRequest());
		
	}

}
