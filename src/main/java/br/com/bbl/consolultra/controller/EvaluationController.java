package br.com.bbl.consolultra.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.bbl.consolultra.model.Answer;
import br.com.bbl.consolultra.model.AnswerSelected;
import br.com.bbl.consolultra.model.Evaluation;
import br.com.bbl.consolultra.model.Happening;
import br.com.bbl.consolultra.model.Participant;
import br.com.bbl.consolultra.model.Question;
import br.com.bbl.consolultra.repository.AnswerRepository;
import br.com.bbl.consolultra.repository.AnswerSelectedRepository;
import br.com.bbl.consolultra.repository.EvaluationRepository;
import br.com.bbl.consolultra.repository.HappeningRepository;
import br.com.bbl.consolultra.repository.ParticipantRepository;
import br.com.bbl.consolultra.repository.QuestionRepository;
import br.com.bbl.consolultra.service.Excel;

@Controller
public class EvaluationController {

	@Autowired
	private ParticipantRepository pr;
	
	@Autowired
	private EvaluationRepository er;
	
	@Autowired
	private HappeningRepository hr;
	
	@Autowired
	private QuestionRepository qr;
	
	@Autowired
	private AnswerRepository ar;
	
	@Autowired
	private AnswerSelectedRepository asr;
	
	@RequestMapping(value = "/evalList")
	public ModelAndView list() {
		// Carrega a lista de avaliações
		List<Evaluation> evaluations = new ArrayList<Evaluation>();
		er.findAll().forEach(evaluations::add);
		Collections.sort(evaluations, Collections.reverseOrder());

		// Carrega a lista de avaliações
		List<Participant> participants = new ArrayList<Participant>();
		pr.findAll().forEach(participants::add);

		ModelAndView mv = new ModelAndView("evaluation_list");
		mv.addObject("evaluations", evaluations);
		mv.addObject("participants", participants);
		return mv;
	}
	
	@RequestMapping(value = "/evalForm", method = RequestMethod.GET)
	public ModelAndView form(Integer id) {
		// Se informar um id então carrega do BD
		Evaluation evaluation = (id != null ? er.findById(id).get() : new Evaluation());
		
		ModelAndView mv = new ModelAndView("/evaluation_form");
		mv.addObject("evaluation", evaluation);
		return mv;
	}
	
	@RequestMapping(value = "/evalForm", method = RequestMethod.POST)
	public String save(@Validated Evaluation evaluation, String created) {
		// Se estiver salvando uma avaliação nova automaticamente define como ativo
		evaluation.setActive(true);
		if (evaluation.getId() == null) {
			evaluation.setDate(new Date());
		} else {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				evaluation.setDate(sdf.parse(created));
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}

		er.save(evaluation);
		return "redirect:/evalForm?id=" + evaluation.getId();
	}

	@RequestMapping(value = "/evalDelete")
	public String delete(Integer id) {
		Evaluation evaluation = er.findById(id).get();
		
		// Se a avaliação tiver algum cartão resposta então não deleta, apenas inativa
		if (evaluation.getAnswerCards().size() > 0) {
			// Caso alguém já tenha realizado então só inativa a avaliação
			evaluation.setActive(false);
			er.save(evaluation);
		// Senão, então deleta tudo
		} else {
			for (Happening happening : evaluation.getHappenings()) {
				for (Question question : happening.getQuestions()) {
					for (Answer answer : question.getAnswers()) {
						ar.delete(answer);
					}
					qr.delete(question);
				}
				hr.delete(happening);
			}
			er.delete(evaluation);
		}

		return "redirect:/evalList";
	}
	
	@RequestMapping(value = "/downloadExcel")
	public void downloadExcel(Integer id, HttpServletResponse response, HttpServletRequest request) {
		Evaluation evaluation = er.findById(id).get();
		
		response.setContentType("application/xls");
		response.setHeader("Content-Disposition", "attachment; filename="+ evaluation.getTitle() + ".xlsx");
		new Excel().create(evaluation, response);
		
		/*try {
			response.getOutputStream().close();
			response.getOutputStream().flush();
			RequestDispatcher dispatcher = request.getSession().getServletContext().getRequestDispatcher("/evalList");
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
	
	// ############################### To Question ###############################
	@RequestMapping(value = "/removeQuest")
	public String removeQuest(Integer id) {
		Question question = qr.findById(id).get();
		
		// Antes de deletar a questão precisa deletar suas respostas (dependências)
		for (Answer answer : ar.findByQuestion(question)) {
			// Antes de deletar a pergunta deleta as seleções realizadas pelos participantes
			for (AnswerSelected answerSelected : answer.getAnswerSelecteds()) {
				asr.delete(answerSelected);
			}
			ar.delete(answer);
		}
		
		qr.delete(question);
		return "redirect:/evalForm?id=" + question.getHappening().getEvaluation().getId();
	}	
}
