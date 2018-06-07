package br.harlan.authjwt.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.harlan.authjwt.entities.People;
import br.harlan.authjwt.repositories.PeopleRepository;
import br.harlan.authjwt.services.PeopleService;

@Service
public class PeopleServiceImpl implements PeopleService {

	@Autowired
	private PeopleRepository peopleRepository;
	
	@Override
	public Optional<People> findByEmail(String email) {
		return Optional.ofNullable(peopleRepository.findByEmail(email));
	}
}
