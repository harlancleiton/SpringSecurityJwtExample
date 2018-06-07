package br.harlan.authjwt.factory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import br.harlan.authjwt.entities.People;
import br.harlan.authjwt.enuns.ProfileEnum;
import br.harlan.authjwt.security.JwtUserDetail;

public class JwtUserFactory {

	private JwtUserFactory() {
	}

	/**
	 * 
	 * Converte e gera um JwtUserDetail com base nos dados de um funcionario
	 * 
	 * @param people
	 * @return JwtUserDetail
	 */
	public static JwtUserDetail create(People people) {
		return new JwtUserDetail(people.getId(), people.getEmail(), people.getPassword(),
				mapToGrantedAuthorities(people.getProfile()));
	}

	/**
	 * Converte o perfil do usuário para o formato utilizado pelo Spring Security
	 * 
	 * @param profile
	 * @return
	 */
	private static List<GrantedAuthority> mapToGrantedAuthorities(ProfileEnum profile) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(profile.toString()));
		return authorities;
	}
}