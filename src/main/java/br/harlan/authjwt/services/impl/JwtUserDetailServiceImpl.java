package br.harlan.authjwt.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.harlan.authjwt.entities.People;
import br.harlan.authjwt.factory.JwtUserFactory;
import br.harlan.authjwt.services.PeopleService;

@Service
public class JwtUserDetailServiceImpl implements UserDetailsService {

	@Autowired
	PeopleService peopleService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<People> people = peopleService.findByEmail(username);

		if (people.isPresent())
			return JwtUserFactory.create(people.get());
		throw new UsernameNotFoundException("Email não cadastrado.");
	}

}
