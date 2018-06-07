package br.harlan.authjwt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.harlan.authjwt.entities.People;

@Transactional(readOnly = true)
public interface PeopleRepository extends JpaRepository<People, Integer> {
	People findByEmail(String email);
}
