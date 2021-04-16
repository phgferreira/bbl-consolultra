package br.com.bbl.consolultra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.bbl.consolultra.model.Participant;
import br.com.bbl.consolultra.repository.ParticipantRepository;

@Controller
public class ParticipantController {

	@Autowired
	private ParticipantRepository pr;
	
	@RequestMapping(value = "/partForm", method = RequestMethod.GET)
	private ModelAndView partForm(String crm) {
		
		// Cria um participante novo só com o CRM
		Participant participant = new Participant(crm);

		ModelAndView mv = new ModelAndView("participant_form");
		mv.addObject("participant", participant);
		mv.addObject("first", true);
		return mv;
	}
	
	@RequestMapping(value = "/partForm", method = RequestMethod.POST)
	private String partSave(@Validated Participant participant) {
		pr.save(participant);
		// Agora que tem o participante salvo podemos verificar avaliação pendente
		return "redirect:/home?id=" + participant.getId();
	}
	
	@RequestMapping(value = "/term")
	private String term() {
		return "term";
	}
}
