package br.com.bbl.consolultra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bbl.consolultra.model.Evaluation;

public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {
	
}
