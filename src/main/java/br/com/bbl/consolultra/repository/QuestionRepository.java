package br.com.bbl.consolultra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bbl.consolultra.model.Happening;
import br.com.bbl.consolultra.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

	Iterable<Question> findByHappening(Happening h);
	
}
