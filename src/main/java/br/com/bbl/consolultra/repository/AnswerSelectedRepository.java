package br.com.bbl.consolultra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bbl.consolultra.model.Answer;
import br.com.bbl.consolultra.model.AnswerSelected;
import br.com.bbl.consolultra.model.Question;

public interface AnswerSelectedRepository extends JpaRepository<AnswerSelected, Integer> {

	List<AnswerSelected> findByQuestion(Question question);
	
	List<AnswerSelected> findByAnswer(Answer answer);
}
