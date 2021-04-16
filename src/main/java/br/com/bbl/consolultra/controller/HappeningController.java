package br.com.bbl.consolultra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.bbl.consolultra.model.Answer;
import br.com.bbl.consolultra.model.Evaluation;
import br.com.bbl.consolultra.model.Happening;
import br.com.bbl.consolultra.model.Question;
import br.com.bbl.consolultra.repository.AnswerRepository;
import br.com.bbl.consolultra.repository.EvaluationRepository;
import br.com.bbl.consolultra.repository.HappeningRepository;
import br.com.bbl.consolultra.repository.QuestionRepository;

@Controller
public class HappeningController {
	
	@Autowired
	private EvaluationRepository er;

	@Autowired
	private HappeningRepository hr;
	
	@Autowired
	private QuestionRepository qr;
	
	@Autowired
	private AnswerRepository ar;

	@RequestMapping(value = "/happForm", method = RequestMethod.GET)
	private ModelAndView form(Integer id, Integer idEvaluation) {
		Happening happening = null;
		if (id == null && idEvaluation != null) {
			happening = new Happening();
			Evaluation evaluation = er.findById(idEvaluation).get();
			happening.setEvaluation(evaluation);
		} else {
			happening = hr.findById(id).get();
		}
		
		ModelAndView mv = new ModelAndView("happening_form");
		mv.addObject("happening", happening);
		return mv;
	}
	
	@RequestMapping(value = "/happForm", method = RequestMethod.POST)
	private String save(@Validated Happening happening, Integer idEvaluation) {
		// Carrega a avaliação pelo ID e insere no caso
		Evaluation evaluation = er.findById(idEvaluation).get();
		happening.setEvaluation(evaluation);
		
		hr.save(happening);
		
		return "redirect:/happForm?id=" + happening.getId();
	}
	
	@RequestMapping("happDelete")
	private String delete(Integer id) {
		Happening happening = hr.findById(id).get();
		
		for (Question question : happening.getQuestions()) {
			for (Answer answer : question.getAnswers()) {
				ar.delete(answer);
			}
			qr.delete(question);
		}
		hr.delete(happening);
		
		return "redirect:/evalForm?id=" + happening.getEvaluation().getId();
	}
}
