package com.mercadolivre.mercadolivre.adicionarpergunta;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Emails {

	@Autowired
	private Mailer mailer;

	public void novaPergunta(@NotNull @Valid Pergunta pergunta) {
		mailer.send("<html>...</html>", "Nova pergunta...", pergunta.getInteressada().getEmail(),
				"novapergunta@nossomercadolivre.com", pergunta.getDonoProduto().getLogin());
	}

}
