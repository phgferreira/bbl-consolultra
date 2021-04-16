package br.com.bbl.consolultra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.bbl.consolultra.model.Answer;
import br.com.bbl.consolultra.model.Question;
import br.com.bbl.consolultra.repository.AnswerRepository;
import br.com.bbl.consolultra.repository.QuestionRepository;

@Controller
public class AnswerController {

	@Autowired
	private QuestionRepository qr;
	
	@Autowired
	private AnswerRepository ar;
	
	@RequestMapping(value = "/addAnswer", method = RequestMethod.POST)
	public String save(@Validated Answer answer, Integer idQuestion, String correct) {
		Question question = qr.findById(idQuestion).get();
		answer.setQuestion(question);

		// Se for null prefedine como falso
		if (answer.getCorrect() == null)
			answer.setCorrect(false);
		
		ar.save(answer);
		return "redirect:/questForm?id=" + question.getId();
	}
	
	@RequestMapping(value = "/removeAnswer")
	public String delete(Integer id) {
		Answer answer = ar.findById(id).get();

		ar.delete(answer);

		return "redirect:/questForm?id=" + answer.getQuestion().getId();
	}

}
