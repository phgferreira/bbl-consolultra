package br.com.bbl.consolultra.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.bbl.consolultra.model.Answer;
import br.com.bbl.consolultra.model.AnswerSelected;
import br.com.bbl.consolultra.model.Failed;
import br.com.bbl.consolultra.model.Question;
import br.com.bbl.consolultra.repository.AnswerRepository;
import br.com.bbl.consolultra.repository.AnswerSelectedRepository;
import br.com.bbl.consolultra.repository.FailedRepository;
import br.com.bbl.consolultra.repository.QuestionRepository;

@Controller
public class AnswerController {

	@Autowired
	private QuestionRepository qr;
	
	@Autowired
	private AnswerRepository ar;
	
	@Autowired
	private AnswerSelectedRepository asr;
	
	@Autowired
	private FailedRepository fr;
	
	@RequestMapping(value = "/addAnswer", method = RequestMethod.POST)
	public String save(@Validated Answer answer, Integer idQuestion, String correct) {
		try {
			Question question = qr.findById(idQuestion).get();
			answer.setQuestion(question);

			// Se for null prefedine como falso
			if (answer.getCorrect() == null)
				answer.setCorrect(false);
			
			ar.save(answer);
			return "redirect:/questForm?id=" + question.getId();

		} catch (Exception e) {
			Failed failed = new Failed(e.getMessage());
			fr.save(failed);
			return "redirect:/failed?id=" + failed.getId();
		}
		
	}
	
	@RequestMapping(value = "/removeAnswer")
	public String delete(Integer id) {
		try {
			Answer answer = ar.findById(id).get();
			
			List<AnswerSelected> answersSelecteds = new ArrayList<AnswerSelected>();
			asr.findByAnswer(answer).forEach(answersSelecteds::add);
			for (AnswerSelected answerSelected : answersSelecteds) {
				asr.delete(answerSelected);
			}

			ar.delete(answer);
			return "redirect:/questForm?id=" + answer.getQuestion().getId();
		} catch (Exception e) {
			Failed failed = new Failed(e.getMessage());
			fr.save(failed);
			return "redirect:/failed?id=" + failed.getId();
		}
	}

}
