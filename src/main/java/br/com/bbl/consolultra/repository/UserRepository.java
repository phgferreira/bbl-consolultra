package br.com.bbl.consolultra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bbl.consolultra.model.UserAccount;

public interface UserRepository extends JpaRepository<UserAccount, Integer> {

	UserAccount findByUsername(String username);
}
