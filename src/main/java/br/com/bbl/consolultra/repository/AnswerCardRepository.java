package br.com.bbl.consolultra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bbl.consolultra.model.AnswerCard;
import br.com.bbl.consolultra.model.Evaluation;
import br.com.bbl.consolultra.model.Participant;

public interface AnswerCardRepository extends JpaRepository<AnswerCard, Integer> {

	List<AnswerCard> findByEvaluation(Evaluation e);
	
	AnswerCard findByEvaluationAndParticipant(Evaluation e, Participant p);

}
