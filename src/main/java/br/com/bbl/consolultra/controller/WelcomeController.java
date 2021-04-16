package br.com.bbl.consolultra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.bbl.consolultra.model.Participant;
import br.com.bbl.consolultra.repository.ParticipantRepository;

@Controller
public class WelcomeController {
	
	@Autowired
	private ParticipantRepository pr;
	
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	private String welcome() {
		return "welcome";
	}
	
	@RequestMapping(value = "/welcome", method = RequestMethod.POST)
	private String redirect(String crm) {
		
		// Verifica se já tem cadastro
		Participant participant = pr.findByCrm(crm);
		if (participant != null) {
			// Se sim verifica pendência de avaliação
			return "redirect:/home?id=" + participant.getId();
		} else {
			// Senão encaminha para o cadastro de participante
			return "redirect:/partForm?crm=" + crm;
		}
	}
	
	@RequestMapping("/about")
	private String about() {
		return "about";
	}

	@RequestMapping("/articles")
	private String articles() {
		return "articles";
	}

	
}
