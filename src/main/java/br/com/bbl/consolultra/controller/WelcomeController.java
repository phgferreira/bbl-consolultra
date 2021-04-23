package br.com.bbl.consolultra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.bbl.consolultra.model.Failed;
import br.com.bbl.consolultra.model.Participant;
import br.com.bbl.consolultra.repository.FailedRepository;
import br.com.bbl.consolultra.repository.ParticipantRepository;

@Controller
public class WelcomeController {
	
	@Autowired
	private ParticipantRepository pr;
	
	@Autowired
	private FailedRepository fr;
	
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	private String welcome() {
		return "welcome";
	}
	
	@RequestMapping(value = "/welcome", method = RequestMethod.POST)
	private String redirect(String crm) {
		try {
			// Verifica se já tem cadastro
			Participant participant = pr.findByCrm(crm);
			if (participant != null) {
				// Se sim verifica pendência de avaliação
				return "redirect:/home?id=" + participant.getId();
			} else {
				// Senão encaminha para o cadastro de participante
				return "redirect:/partForm?crm=" + crm;
			}
		} catch (Exception e) {
			Failed failed = new Failed(e.getMessage());
			fr.save(failed);
			return "redirect:/failed?id=" + failed.getId();
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
