package br.com.bbl.consolultra.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.bbl.consolultra.model.AnswerCard;
import br.com.bbl.consolultra.model.Evaluation;
import br.com.bbl.consolultra.model.Participant;
import br.com.bbl.consolultra.repository.EvaluationRepository;
import br.com.bbl.consolultra.repository.ParticipantRepository;

@Controller
public class HomeController {
	
	@Autowired
	private ParticipantRepository pr;
	
	@Autowired
	private EvaluationRepository er;

	@RequestMapping(value = "/home")
	private ModelAndView home(Integer id) {
		Participant participant = pr.findById(id).get();

		// Carrega todas as avaliações
		List<Evaluation> evaluations = new ArrayList<Evaluation>();
		er.findAll().iterator().forEachRemaining(evaluations::add);

		// Remove da lista as avaliações inativadas
		for (Evaluation evaluation : evaluations) {
			if (!evaluation.getActive()) {
				evaluations.remove(evaluation);
			}
		}

		// Remove da lista de avaliações o que já foi iniciado
		for (AnswerCard answerCards : participant.getAnswerCards()) {
			evaluations.remove(answerCards.getEvaluation());
		}
		
		ModelAndView mv = new ModelAndView("home");
		mv.addObject("participant", participant);
		mv.addObject("evaluations", evaluations);
		return mv;
		
	}

	@RequestMapping(value = "/pdf/{fileName:.+}", method = RequestMethod.GET, produces = "application/pdf")
	public ResponseEntity<InputStreamResource> article(@PathVariable("fileName") String fileName) throws IOException {
		ClassPathResource pdfFile = new ClassPathResource("pdf/" + fileName);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Access-Control-Allow-Methods", "GET, POST, PUT");
		headers.add("Access-Control-Allow-Headers", "Content-Type");
		headers.add("Content-Disposition", "filename=" + fileName);
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");

		headers.setContentLength(pdfFile.contentLength());
		ResponseEntity<InputStreamResource> response = new ResponseEntity<InputStreamResource>(
				new InputStreamResource(pdfFile.getInputStream()), headers, HttpStatus.OK);
		return response;

	}

}
