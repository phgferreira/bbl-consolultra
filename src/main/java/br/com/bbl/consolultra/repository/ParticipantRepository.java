package br.com.bbl.consolultra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bbl.consolultra.model.Participant;

public interface ParticipantRepository extends JpaRepository<Participant, Integer> {

	Participant findByCrm(String crm);
}
