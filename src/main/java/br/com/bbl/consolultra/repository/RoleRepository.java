package br.com.bbl.consolultra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bbl.consolultra.model.Role;
import br.com.bbl.consolultra.model.UserAccount;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Role findByName(String name);
	
	List<Role> findByUsers(UserAccount user);
}
