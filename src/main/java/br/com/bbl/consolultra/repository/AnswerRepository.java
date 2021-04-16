package br.com.bbl.consolultra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bbl.consolultra.model.Answer;
import br.com.bbl.consolultra.model.Question;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {

	List<Answer> findByQuestion(Question e);
}
