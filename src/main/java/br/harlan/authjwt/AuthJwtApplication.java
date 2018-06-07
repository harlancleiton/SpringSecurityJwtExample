package br.harlan.authjwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.harlan.authjwt.entities.People;
import br.harlan.authjwt.enuns.ProfileEnum;
import br.harlan.authjwt.repositories.PeopleRepository;
import br.harlan.authjwt.utils.PasswordUtils;

@SpringBootApplication
public class AuthJwtApplication {

	@Autowired
	private PeopleRepository peopleRepository;

	public static void main(String[] args) {
		SpringApplication.run(AuthJwtApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			People admin = new People();
			admin.setEmail("harlancleiton@gmail.com");
			admin.setProfile(ProfileEnum.ROLE_ADMIN);
			admin.setName("Harlan");
			admin.setPassword(PasswordUtils.generateBCrypt("12345"));
			peopleRepository.save(admin);
			
			People client = new People();
			client.setEmail("harlan@itech.net.br");
			client.setProfile(ProfileEnum.ROLE_CLIENT);
			client.setName("Harlan");
			client.setPassword(PasswordUtils.generateBCrypt("12345"));
			peopleRepository.save(client);
		};
	}
}
