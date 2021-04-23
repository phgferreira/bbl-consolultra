package br.com.bbl.consolultra.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.bbl.consolultra.model.Answer;
import br.com.bbl.consolultra.model.AnswerCard;
import br.com.bbl.consolultra.model.AnswerCardState;
import br.com.bbl.consolultra.model.AnswerSelected;
import br.com.bbl.consolultra.model.Evaluation;
import br.com.bbl.consolultra.model.Failed;
import br.com.bbl.consolultra.model.Happening;
import br.com.bbl.consolultra.model.Participant;
import br.com.bbl.consolultra.model.Question;
import br.com.bbl.consolultra.repository.AnswerCardRepository;
import br.com.bbl.consolultra.repository.AnswerRepository;
import br.com.bbl.consolultra.repository.AnswerSelectedRepository;
import br.com.bbl.consolultra.repository.EvaluationRepository;
import br.com.bbl.consolultra.repository.FailedRepository;
import br.com.bbl.consolultra.repository.ParticipantRepository;

@Controller
public class DoingController {
	
	@Autowired
	private EvaluationRepository er;
	
	@Autowired
	private ParticipantRepository pr;
	
	@Autowired
	private AnswerCardRepository acr;
	
	@Autowired
	private AnswerSelectedRepository asr;
		
	@Autowired
	private AnswerRepository ar;
	
	@Autowired
	private FailedRepository fr;
	
	@RequestMapping(value = "/init")
	private String init(Integer idParticipant, Integer idEvaluation) {
		try {
			// Carrega as dependências
			Evaluation evaluation = er.findById(idEvaluation).get();
			Participant participant = pr.findById(idParticipant).get();
			
			// Separa a lista de questões
			List<Question> questions = new ArrayList<Question>();
			for (Happening happening : evaluation.getHappenings()) {
				for (Question question : happening.getQuestions()) {
					questions.add(question);
				}
			}
			// Embaralha as questões
			Collections.shuffle(questions);
	
			// Cria o cartão de resposta
			AnswerCard answerCard = new AnswerCard(new Date(), AnswerCardState.PROGRESS, evaluation, participant);
			acr.save(answerCard);
	
			// insere as questões já embaralhadas no cartão resposta
			for (Question question : questions) {
				AnswerSelected answerSelected = new AnswerSelected(answerCard, question);
				asr.save(answerSelected);
			}
		
			// Constroe a URL personalizda com a ordem
			return "redirect:/next?idAnswerCard=" + answerCard.getId();

		} catch (Exception e) {
			Failed failed = new Failed(e.getMessage());
			fr.save(failed);
			return "redirect:/failed?id=" + failed.getId();
		}
	}
	
	@RequestMapping(value = "/next", method = RequestMethod.GET)
	private ModelAndView next(Integer idAnswerCard) {
		try {
			// Carrega o cartão resposta
			AnswerCard answerCard = acr.findById(idAnswerCard).get();
			
			// Verifica se tem alguma questão pendente ainda
			for (AnswerSelected answerSelected : answerCard.getAnswerSelecteds()) {
				// Se ainda não tiver resposta
				if (answerSelected.getAnswer() == null) {
					ModelAndView mv = new ModelAndView("next_question");
					mv.addObject("answerSelected", answerSelected);
					return mv;
				}
			}
			
			// Se não houver mais questões pendentes então finaliza
			answerCard.setState(AnswerCardState.COMPLETE);
			acr.save(answerCard);
			
			ModelAndView mv = new ModelAndView("finish_evaluation");
			mv.addObject("answerCard", answerCard);
			return mv;
		} catch (Exception e) {
			Failed failed = new Failed(e.getMessage());
			fr.save(failed);
			ModelAndView mv = new ModelAndView("failed");
			mv.addObject("message", failed.getMessage());
			return mv;
		}
	}
	
	@RequestMapping(value = "/answering")
	private String answering(Integer idAnswer, Integer idAnswerSelected) {
		try {
			// Carerga os dados
			Answer answer = ar.findById(idAnswer).get();		
			AnswerSelected answerSelected = asr.findById(idAnswerSelected).get();
		
			answerSelected.setAnswer(answer);
			asr.save(answerSelected);
		
			return "redirect:/next?idAnswerCard=" + answerSelected.getAnswerCard().getId();

		} catch (Exception e) {
			Failed failed = new Failed(e.getMessage());
			fr.save(failed);
			return "redirect:/failed?id=" + failed.getId();
		}
	}
}
