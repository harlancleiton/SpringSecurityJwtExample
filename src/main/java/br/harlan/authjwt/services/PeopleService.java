package br.harlan.authjwt.services;

import java.util.Optional;

import br.harlan.authjwt.entities.People;

public interface PeopleService {
	Optional<People> findByEmail(String email);
}
