package br.com.bbl.consolultra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bbl.consolultra.model.Happening;

public interface HappeningRepository extends JpaRepository<Happening, Integer> {
	
}
