package br.com.bbl.consolultra.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import br.com.bbl.consolultra.model.UserAccount;
import br.com.bbl.consolultra.repository.RoleRepository;
import br.com.bbl.consolultra.repository.UserRepository;

@Repository
@Transactional
public class ImplementsUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository ur;
	
	@Autowired
	private RoleRepository rr;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserAccount user = ur.findByUsername(username);
		user.setRoles(rr.findByUsers(user));
		
		return user;
	}

}
