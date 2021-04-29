package br.com.bbl.consolultra;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.bbl.consolultra.model.Role;
import br.com.bbl.consolultra.model.UserAccount;
import br.com.bbl.consolultra.repository.RoleRepository;
import br.com.bbl.consolultra.repository.UserRepository;

@SpringBootApplication
public class ConsolultraApplication implements CommandLineRunner {

	@Autowired
	private UserRepository ur;
	
	@Autowired
	private RoleRepository rr;
	
	@Autowired
	private PasswordEncoder pe;

	public static String PORT = System.getenv("PORT");
	
	public static void main(String[] args) {
		SpringApplication.run(ConsolultraApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		try {

			UserAccount adminUser = ur.findByUsername("admin");
			if (adminUser == null) {
				adminUser = new UserAccount();
				adminUser.setName("Administrador");
				adminUser.setUsername("admin");
				adminUser.setPassword(pe.encode("L@bronic1"));
				ur.save(adminUser);
			}
			
			Role roleAdmin = rr.findByName("ROLE_ADMIN");
			if (roleAdmin == null) {
				roleAdmin = new Role("ROLE_ADMIN");
				
				// Adiciona o usuário admin nesse papel
				List<UserAccount> admins = new ArrayList<UserAccount>();
				admins.add(adminUser);
				roleAdmin.setUsers(admins);
				
				// Salva o papel
				rr.save(roleAdmin);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("## Erro no Start ADMIN e ROLE_ADMIN ##");
		}
		
	}

}
