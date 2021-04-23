package br.com.bbl.consolultra.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.bbl.consolultra.exception.LossLinkWithEvaluation;
import br.com.bbl.consolultra.model.Answer;
import br.com.bbl.consolultra.model.AnswerSelected;
import br.com.bbl.consolultra.model.Failed;
import br.com.bbl.consolultra.model.Happening;
import br.com.bbl.consolultra.model.Question;
import br.com.bbl.consolultra.repository.AnswerRepository;
import br.com.bbl.consolultra.repository.AnswerSelectedRepository;
import br.com.bbl.consolultra.repository.FailedRepository;
import br.com.bbl.consolultra.repository.HappeningRepository;
import br.com.bbl.consolultra.repository.QuestionRepository;

@Controller
public class QuestionController {
	
	@Autowired
	private HappeningRepository hr;

	@Autowired
	private QuestionRepository qr;
	
	@Autowired
	private AnswerRepository ar;
	
	@Autowired
	private AnswerSelectedRepository asr;
	
	@Autowired
	private FailedRepository fr;
	
	@RequestMapping(value = "/questForm", method = RequestMethod.GET)
	public ModelAndView form(Integer id, Integer idHappening) throws UnsupportedEncodingException {
		try {
			Question question = null;
			if (id == null && idHappening != null) {
				question = new Question();
				Happening happening = hr.findById(idHappening).get();
				question.setHappening(happening);
			} else {
				question = qr.findById(id).get();
			}
			
			ModelAndView mv = new ModelAndView("question_form");
			mv.addObject("question", question);
			return mv;
		} catch (Exception e) {
			Failed failed = new Failed(e.getMessage());
			fr.save(failed);
			ModelAndView mv = new ModelAndView("failed");
			mv.addObject("message", failed.getMessage());
			return mv;
		}
	}
	
	@RequestMapping(value = "/questForm", method = RequestMethod.POST)
	public String save(@Validated Question question, MultipartFile file, Integer idHappening, BindingResult result, RedirectAttributes attributes) throws IOException, LossLinkWithEvaluation, InterruptedException {
		try {
			Happening happening = hr.findById(idHappening).get();
			question.setHappening(happening);
	
			// Se receber um arquivo novo, atualiza
			if (!file.isEmpty())
				question.setImg(file.getBytes());
			// Se não receber uma nova imagem recarregar a existente
			else if (question.getId() != null) {
				Question savedQuestion = qr.findById(question.getId()).get();
				question.setImg(savedQuestion.getImg());
			}
			
			qr.save(question);
			return "redirect:/questForm?id=" + question.getId();
		} catch (Exception e) {
			Failed failed = new Failed(e.getMessage());
			fr.save(failed);
			return "redirect:/failed?id=" + failed.getId();
		}
	}
	
	@RequestMapping("questDelete")
	public String delete(Integer id) {
		try {
			Question question = qr.findById(id).get();
			
			List<AnswerSelected> answersSelecteds = new ArrayList<AnswerSelected>();
			asr.findByQuestion(question).forEach(answersSelecteds::add);
			for (AnswerSelected answerSelected : answersSelecteds) {
				asr.delete(answerSelected);
			}
			
			for (Answer answer : question.getAnswers()) {
				ar.delete(answer);
			}
			qr.delete(question);
			return "redirect:/happForm?id=" + question.getHappening().getId();
		} catch (Exception e) {
			Failed failed = new Failed(e.getMessage());
			fr.save(failed);
			return "redirect:/failed?id=" + failed.getId();
		}
	}
}
